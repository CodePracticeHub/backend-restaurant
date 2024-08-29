# Use the official MySQL image from Docker Hub
FROM mysql:5.7
LABEL "Project"="Restaurant Management"
LABEL "Author"="EdyMol"

# Set environment variables for MySQL
ENV MYSQL_ROOT_PASSWORD=MyPassword123!
ENV MYSQL_DATABASE=restaurant-management
#ENV MYSQL_USER=
#ENV MYSQL_PASSWORD=

# Copy the SQL scripts into the Docker container
# This will execute the SQL script automatically when the container is started
#COPY mysql.sql /docker-entrypoint-initdb.d/mysql.sql

# Expose the default MySQL port
EXPOSE 3306

# Use the default MySQL entrypoint
CMD ["mysqld"]