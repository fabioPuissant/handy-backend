# Use the official PostgreSQL image as a base
FROM postgres:latest

# Set environment variables for the default database and user
# Replace 'mydatabase', 'myuser', and 'mypassword' with your desired values
ENV POSTGRES_DB=handy
ENV POSTGRES_USER=fabio
ENV POSTGRES_PASSWORD=letmein
# Copy the database seed script
COPY ./init/seed.sql /docker-entrypoint-initdb.d/seed.sql

# cli command to run = docker run --name handy-postgres-container -p 5432:5432 -d handypostgres
# cli command to run = docker run --name handy-postgres-seeded -p 5432:5432 -d handypostgres-seeded