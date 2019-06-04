#!/bin/bash

#----Use below for template to create new db creation shell script----#
# BASEDIR=$(dirname $0)
# psql -U postgres -f "$BASEDIR/dropdb.sql" &&
# createdb -U postgres capstone &&
# psql -U postgres -d capstone -f "$BASEDIR/schema.sql" &&
# psql -U postgres -d capstone -f "$BASEDIR/user.sql" &&
# psql -U postgres -d capstone -f "$BASEDIR/data.sql"
#---------------------------------------------------------------------#

BASEDIR='C:\Users\tedwa\workspace\ThirstyCat\database'
psql -U postgres -f "$BASEDIR/createdb.sql" &&
# psql -U postgres -d thirstycat -f "$BASEDIR/schema.sql" && #Commented out because
                                                             #Spring Boot JPA app creates tables based on model classes
psql -U postgres -d thirstycat -f "$BASEDIR/user.sql"