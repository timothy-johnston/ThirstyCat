-- ********************************************************************************
-- This script creates the database users and grants them the necessary permissions
-- ********************************************************************************

CREATE USER capstone_owner WITH PASSWORD 'capstone_owner1';

GRANT ALL 
ON ALL TABLES IN SCHEMA public
TO capstone_owner;

GRANT ALL 
ON ALL SEQUENCES IN SCHEMA public
TO capstone_owner; 

CREATE USER capstone_appuser WITH PASSWORD 'capstone_appuser1';

GRANT SELECT, INSERT, UPDATE, DELETE
ON ALL TABLES IN SCHEMA public
TO capstone_appuser;

GRANT USAGE, SELECT
ON ALL SEQUENCES IN SCHEMA public
TO capstone_appuser; 

CREATE USER postgres WITH PASSWORD 'postgres1';

GRANT ALL 
ON ALL TABLES IN SCHEMA public
TO postgres;

GRANT ALL 
ON ALL SEQUENCES IN SCHEMA public
TO postgres;