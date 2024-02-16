### Welcome to backend-restaurant-project
# IMPORTANT
## BEFORE START WORKING, DO THE FOLLOWING STEPS. 
- ``` git checkout main ```
- ```git pull origin main```
- ```git checkout <branch_name>```
- ```git merge main```
### That will ensure all branches are up to date with the main branch

### Steps to Create a Pull Request for the 'workingBranch' Branch:
* ```git checkout -b your_branch```
* ```git add .```
* ```git commit -m "Your commit message"```
* ```git push origin your_branch```

#### Create Pull Request:

- Navigate to your repository on the GitHub.
- Create a pull request from the 'menu' branch to the main branch.
- Provide a meaningful title and description for the pull request.

## How to update your brand to main branch
## To bring the changes from the main branch into your menu branch, you should use git merge.
### The easy way 
* ```git checkout main```
* ```git pull origin main```
* ```git checkout your_branch```
* ```git pull origin your_branch```

### In your branch, you can also use
* ```git merge main```

### The harder way
* ```git checkout your_branch```
* ```git fetch origin main```
* ```git merge origin/main```
* ```git add .```
* ```git commit -m "Merge changes from main into your_branch"```
* ```git push origin your_branch```


## Please work on a branch already created for you based on your entity
* If the entity you are working on is not created, please create it. 

### Create a database with the name of "restaurantdb"
``` CREATE DATABASE restaurantdb;```

### Clone the repository by using this in your CLI
``` git clone https://github.com/SpringBootDevs/backend-restaurant-project-.git ```

### Add the dependencies you need using Maven Repositories website

