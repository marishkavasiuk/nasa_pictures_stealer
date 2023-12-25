CREATE SCHEMA IF NOT EXISTS credit_advisory;

DO $$ BEGIN
CREATE TYPE credit_advisory.advisors_role AS ENUM('associate', 'partner', 'senior');
        EXCEPTION WHEN duplicate_object THEN NULL;
END $$;

CREATE TABLE IF NOT EXISTS credit_advisory.advisors (
    id SERIAL CONSTRAINT PK_advisors PRIMARY KEY,
    email VARCHAR(255) NOT NULL CONSTRAINT UQ_advisors_email UNIQUE,
    username VARCHAR(255) NOT NULL,
    role advisors_role NOT NULL,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE IF NOT EXISTS credit_advisory.applicants (
    id SERIAL CONSTRAINT PK_applicants PRIMARY KEY,
    email VARCHAR(255) NOT NULL CONSTRAINT UQ_applicants_email UNIQUE,
    username VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    social_security_number VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL
);

CREATE TABLE IF NOT EXISTS credit_advisory.addresses (
    applicant_id INT CONSTRAINT PK_addresses PRIMARY KEY,
    city VARCHAR(255),
    street VARCHAR(255),
    number VARCHAR(10),
    zip VARCHAR(10),
    apt INT,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL,
    CONSTRAINT FK_addresses_applicants FOREIGN KEY(applicant_id) REFERENCES applicants ON DELETE CASCADE
);

DO $$ BEGIN
CREATE TYPE credit_advisory.phone_types AS ENUM('mobile', 'home', 'work');
        EXCEPTION WHEN duplicate_object THEN NULL;
END $$;

CREATE TABLE IF NOT EXISTS credit_advisory.phones(
    id serial CONSTRAINT PK_phones PRIMARY KEY,
    number VARCHAR(10) NOT NULL,
    phone_type credit_advisory.phone_types,
    created_at TIMESTAMP DEFAULT NOW() NOT NULL,
    applicant_id INT NOT NULL CONSTRAINT FK_phones_applicants REFERENCES applicants ON DELETE CASCADE

    );

DO $$ BEGIN
CREATE TYPE credit_advisory.application_status AS ENUM('new', 'assigned', 'on_hold', 'approved', 'canceled', 'declined');
    EXCEPTION WHEN duplicate_object THEN NULL;
END $$;

CREATE TABLE IF NOT EXISTS credit_advisory.applications (
    id SERIAL CONSTRAINT PK_applications PRIMARY KEY,
    amount DECIMAL,
    status credit_advisory.application_status DEFAULT 'new',
    created_at TIMESTAMP DEFAULT NOW() NOT NULL,
    assigned_at TIMESTAMP,
    applicant_id INT NOT NULL CONSTRAINT FK_applications_applicants REFERENCES applicants,
    advisor_id INT CONSTRAINT FK_applications_advisors REFERENCES advisors
);

CREATE OR REPLACE FUNCTION update_assigned_at() RETURNS TRIGGER AS $$
BEGIN
    IF NEW.advisor_id IS NOT NULL AND NEW.assigned_at IS NULL THEN
         NEW.assigned_at := now();
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER update_assigned_at_trigger
BEFORE INSERT OR UPDATE ON credit_advisory.applications
FOR EACH ROW EXECUTE FUNCTION update_assigned_at();