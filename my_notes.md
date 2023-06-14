**My SQL:**

SHOW DATABASES;  ---to check available databases

SELECT DATABASE() FROM DUAL; --to check current database


CREATE TABLE IF NOT EXISTS music (id INT AUTO_INCREMENT PRIMARY KEY, artist VARCHAR(128), album VARCHAR(128)); 
 --- to create a table


SHOW TABLES;
DESCRIBE music;
INSERT INTO music (artist, album) VALUES ('me','shower'),('joe','IGI'),('Max Pain','Pain');
SELECT * FROM music LIMIT 5 OFFSET 0;


Amplify Course used commands:-

amplify upgrade  .....to upgrade amplify

**Amplify Studio Course:-**

npx create-react-app <file name where app to be created> ......to create new react app inside a existing file
npm install -g @aws-amplify/cli  ....... to install amplify cli in the folder(need to run iside the my react app folder first time)
amplify configure  ........................to configure amplify account

npm start ...................to start the app in local server (note need to be inside the project folder)
npm install aws-amplify @aws-amplify/ui-react   to install amplify Ui tools in the folder(need to run iside the my react app folder first time)
amplify pull --appId d1tj672dnbrkij --envName staging ..... to pull all the component from amplify studio remote files to local project dir
amplify pull

amplify delete .....to clear the project from cloud and local
 



**GraphQl coursce:-**

npm init    .................to add node dependencies in a project package folder(creates package.json)
npm install express --save ...........to install express dependencies in the project helped to create the localhost in any port
npm install graphql express-graphql ........ to install express version of graphql to use  the graphiql inteface
npm install lodash --save ........to install lodash which will help to query arround data easyly
node app   ....................to run the app js
npm install nodemon -g ..........to install node mod which will run and refresh the listner continuesly so that we do not require to run node app again and again and our chenges will reflect directly
nodemon app  ........to run app continuously and refresh to reflect the code changes 
npm install mongoose    .....to install mongoose dpendency which will help to connect with mongoDB or db
npm install cors ............to install cors which will help to access the api in server


(
 ,"scripts":{ //add this before uploading it to server
    "start": "node app.js"
  }
) add this code before uploading your api code to webserver in package.json file after dependency

npx create-next-app <name of folder>  .....to create a app in next js 
npm run dev .......to start the dev server
npm run build .......builds the app for production
npm start .......run the built app in production mode

npm install -D tailwindcss postcss autoprefixer  .....to intall tail wind css
npx tailwindcss init -p ...........creates tailwnd.config.js and postcss.config.js file
and follow the installation guid in the tailwindcss website

amplify init .......to connect with appsync 
npm install aws-amplify @aws-amplify/ui-react@1.x.x   ....to install amplify react dependency
npm upgrade aws-amplify @aws-amplify/ui-react  .......to upgrade 
amplify add api  ...........to add api code in local code 
amplify push .....to push all the changes in out code
amplify push --allow-destructive-graphql-schema-updates ......to deleate a schema and create new schema if we intened to do it.(it will deleate all the data exist and our current table and create new data table)
amplify console api .........to launche the appsync consol for api so we can build api in appsync console any time
amplify add auth ....to add authentication  code in local
amplify remove auth........... to remove authentication

amplify update api ......to update api configuration.
npm install uuid .........to install uuid dependencies to create uniqe identifier
npm install react-simplemde-editor react-markdown  .....to install simple editor and --- dependencies
npm install momento ........to install momento dependency

amplify add storage ....to add s3 storage
amplify mock api ......to test the api

rm -r amplify/mock-data  ..... to remove mock data
amplify configure project ......to configure amplify project settings

amplify override <servisce name>  .....override cdk template for api or other service....



**AWS CDK, Serverless and Typescript:-**

msiexec.exe /i https://awscli.amazonaws.com/AWSCLIV2.msi ........installing or updating aws cli

npm install typescript --save ....to install dependency of type script in a specific project.
npm install typescript -g ......to install typescript globaly to use its compile feature.
npm install -g aws-cdk   .......to install aws cloud development kit

cdk init app --language typescript  ...... to initialize a cdk project with typescript dependency as language
cdk synth   ..............to generate cloud formation template or to refresh our cloud formation templete
cdk bootstrap  ........to create a cloud formation stack in our accunt
cdk deploy   ..........to deploy our stack. it also compile our code
npm install @aws-cdk/aws-s3  .....to install s3 librery

cdk list .............to list out all the stacks
cdk diff .......difference of stack in local code and deployed stacks
cdk distory <stack name>  ........to delete a stack
cdk doctor  ......to check the config of of stacks

cdk deploy --parameters <parameter name>=<parameter value> .......to deploy with parameter vaalue set as any other value then default

aws-cdk project from scratch:-

npm init -y .....to initialize a project with node dependencies
npm i -D aws-cdk aws-cdk-lib constructs ts-node typescript ....to install the dependencies
cdk.json
{
   "app" : "npx infrastructure/Launcher.ts"
}

cdk synth ....
tsc --init ...............to add tsconfig.json file and 
(ENOENT: no such file or directory, open 'cdk.out\manifest.json'
if got this error try npm update)

cdk bootstrap ....
cdk deploy ...
[ npm install --save-dev esbuild@0  .....to install a dependency to use lambda bundle

npm install -D webpack webpack-cli ts-loader @types/webpack   .....to install a dependency to use lambda bundle ] .....use any one

npm install aws-sdk ........to access all the aws services in our code
npm i @types/aws-lambda .............get the types to write our code


aws cognito-idp admin-set-user-password --user-pool-id <user pool id> --username <user name> --password "<new Password>" --permanent    ..........to change user pool users password from comand prompt without varification code.

example: aws cognito-idp admin-set-user-password --user-pool-id ap-south-1_3D2FCta95 --username aks_test --password "Testing@123" --permanent

npm i aws-amplify @aws-amplify/auth  

npm i fs -- install file system dependency

npm update @aws-amplify/cli-extensibility-helper



**Docker:-**

service docker start  :-to start the docker(wont work with rancher)
docker info :- to see the docker details
docker images :-  to see all the images
docker run <image name>:<version (default latest)> :- will try to check for the image locally if not present will pull the image from docker hub

docker run -it <image name> bash:- here "i" intaractive and "t" opens the terminal of container

docker pull <image name>:<version (default latest)> :- to pull the image

docker run -i -d -t -p <local hostport>:<port> <image name> :- to run the image  "d" is for ditached mode so that it won't open the terminal and "p" to specify the port

docker ps :-  to check the container up and running 

docker ps -a :- to check all the container

docker stop <container id> :- to stop the container

docker rm <container ids> :- to remove the container(container should not e running)

docker rm -f <container ids> :- to remove the container even if the container is running

docker rmi <Image id> :- to remove images

docker run -itd --name=<name for my container> <image name> :- to run a container with our given name (note image name should be given at the end of the command)

docker start <container id or name>:- to start a container

docker restart <container id or name> :- to restart the container

docker pause <container id or name>: - to pause a container

docker unpause <container id or name>: - to unpause a container

docker commit <container id> <new image name>:- to create your own image from a container(not recomended)

docker exec -it <container id> bash :- to open a bash terminal inside the container

docker history <image> : - to see how the image is created 

docker volume ls : - list out the docker managed volumes

docker volume create <volume name> : - to create a volume

docker run -dit --mount source=<volume name>,destination=/<foldername inside the container> <image name> :- to mount a docker volume inside a container 


docker run -dit -v /<any dir>:/<directory inside the container> <image name> :- to mount a random directory inside a container


docker network ls :- to list out the networks

docker network inspect <network name> :- to see the info of docker network


docker network create <network name> --subnet=<ip range/no of network> : - to create a network 

docker run --name <name of container> -- net <existing network name> --ip <give a ip> -h <host> -p <hostport/containerport> -it <existing image name> /bin/bash :- to run a container with a given network. "/bin/bash" opens the container terminal


docker attach <container name> :- 


docker network disconnect <existing network name> <container name> :- to disconnect the network from a container 

docker network connect <existing network name> <container name> :- to connect the network to a container


docker build -t <my tag to the image> <path of Dockerfile> :- to build a docker image using commands in "Dockerfile" 

mvn clean package -DskipTests

mvn archetype:generate -DgroupId=com.bharath -DartifactId=hellomaven -DarchetypeArtifactId=maven-archetype-quickstart -DinteractiveMode=false

docker-compose up -d :- to start the service

docker-compose stop <service name in yaml file> :- to stop a perticular service

docker-compose start <service name in yaml file> :- to start a perticular service

docker-compose down :- stops all the service and removes the container and network





=================================
docker run -dit -p 9000:8080 --name=mycontainer -v C:\Users\2195410\LearningProject\new-app-test\.aws-sam\build\HelloWorldFunction:/var/task/ 8921f54c83cf com.functions.Hello::hello

curl -XPOST "http://localhost:9000/2015-03-31/functions/function/invocations" -d '{}'


curl -X POST "http://localhost:9000/2015-03-31/functions/function/invocations" --json @events\event.json

docker rm -f mycontainer
 

docker-compose up -d


**app with gradle:**

gradle init 
(select default options)
gradle wrapper


($env:REACT_APP_Environment = "test") -and (npm start)


set "REACT_APP_NOT_SECRET_CODE=abcdef" && npm start


amplify init --app <git repo>

amplify init --app https://github.com/AKS-V02/new-app.git


**SAM template:-**
git clone https://github.com/aws-samples/serverless-patterns/ 
cd serverless-patterns/lambda-dynamodb-ddbstream-lambda-sam-java



**my project front end initialization**


npx create-react-app <file name where app to be created> ......to create new react app inside a existing file

npm i aws-amplify @aws-amplify/auth  

npm install aws-amplify @aws-amplify/ui-react 

npm install aws-sdk

npm install -D tailwindcss postcss autoprefixer  .....to intall tail wind css
npx tailwindcss init -p ...........creates tailwnd.config.js and postcss.config.js file
and follow the installation guid in the tailwindcss website


npm install uuid .........to install uuid dependencies to create uniqe identifier
npm install react-simplemde-editor react-markdown  .....to install simple editor and --- dependencies
npm install momento ........to install momento dependency

npm install react-router-dom

npm i react-icons


**Git bash Commands:-**

create a .gitignore file add unwanted file in it to ignore unwanted files
create a .bash_profile file add alias to make short version of long command

gh repo create ......... 

cd ~    .....to change to root directory
code .   ...............to open project file in default editor
mkdir <new directry name to be created>
pwd
cd <path of directroy>
ls
ls -al .....  to see all . files also
git ls-files   ....to see the files which are being tracked by git     
git ls -al ..........to see all git tracked file including . files
rm -rf <file name to remove>recurcive file deleate
git rm <file name to remove>
mv <new file name> <file name>  .......file rename
git mv <new file name> <file name>  .......file rename and move the changes to staging area ready to commit
git config --global user.name "AKS-V02"
git config --global user.email "a.k.s02@outlook.com"
git config --global --list
git config --global init.defaultBranch <branch name need to be default> .......to change default branch name
git config --global core.editor "notepad++.exe -multiInst -nosession"....to change core editor
git config --global -e .........to open git config file in core editor
git clone https://github.com/AKS-V02/CalculatorApp.git
git status
echo "Test From Course" >> new.txt
cat new.txt
cat ~/.gitconfig   ........................ to check the git config file
git add new.txt
git add .          ..............to add recurcive files 
git add -A
git commit -m "Adding New Text File(commit massage)"  
git commit -am "Adding New Text File(commit massage)"......add and commit together git tracked file 
git push origin main
git push
git pull origin main
git init <Directory name> ........to initialize empty git repo inside the directory
git init -b <branch name like main> <dirctory name>
nano new.txt
git branch -m main .....to change branch name to main
git reset HEAD <file name> ..........to unstage a file
git checkout -- <file name>....to discard the changes in working directory
git log     ............to see commit log
git log --abbrev-commit   ...............to sorten commit id
git log --oneline --graph --decorate .........to get a compact commit log
git log --since="3 days ago"  .....last 3 days commit
git log --follow -- <path of the file> .....to see the files commit history
git show <commit id> .......to see the specific commit
git config --global alias.<alias name for the cmd> "<whole cmd without git>"   ............to create alias command for long commands which could be used in all directory


git diff     ................difference betwen working and staged directory
git diff HEAD ........difference betwen working and last commited directory
git diff --staged HEAD ...difference betwen staged and last commited directory

git diff -- <file path>
git diff <first ref> <second ref>
git diff HEAD HEAD^ .........diff between last commit and commit before that

git diff main origin/main  ....to compair between commited local and remote directory
git branch    ......list out local branches
git branch -a     ........to list out all local and remote branches
git branch <new branch name> .....to create new branch
git branch -b <new branch name> .....to create new branch and make it active
git checkout <branch name> ........to change active branch
git branch -m <old name> <new name> ..... to rename the branch
git branch -d <branch name>  ........to deleate a branch

git merge <branch name> .......to do the fast forward mearge active branch and the branch name given
git merge <branch name> --no-ff  .......to do the mearge active branch and the branch name given disabling fast forward mearge
git merge <branch name> -m "<mearge comment>"

git rebase <branch name> ...... to rebase the active branch with the branch name given
git rebase --abort ..............to abort the rebase command
git fetch origin <branch name> ......to fetch the reference of the branch from remot dir of gitub to my local dir
git pull --rebase origin <branch name> ......to rebase the changes of remote dir to my local dir of the branch

git stash     ......to temporary save the uncomplete changes and remove them from local repository so that we can do some emergency modification and commit them to remote dir
git stash apply .......to get back those last stashed changes
git stash list    ..........to see the stashed changes
git stash drop   ..............to delete the last stashed changes 
git stash -u        ...............to stash untracked files also 
git stash pop     ..........to apply the stash and also drop it

git stash save "stash saving massage"   ........if multiple stashing required then this command is usefull

git stash show stash@{<stash index>}   .......to see the specific stash changes
git stash apply stash@{<stash index>}   .......to apply the specific stash changes
git stash clear    ...............to clear all stashes saved 
git stash branch <branch name>  ......a new branch will be created with stashed changes and branch will be switched to new branch and the stash will be deleted
git tag <tag name> .......to put up a tag on our last commit with given tag name
git tag --list .........to see all tags list
git show <tag name> ....to see the commit details with the tag name
git tag --delete <tag name> .....to delete the tag with that name
git tag -a <tag name> ........to create a annoted tag
git tag -a <tag name> <commit id> ........to create a annoted tag on specified commit id
git commit --amend   ........to change the last commit massage
git tag <tag name> -m "<tag massage>"
git tag -a <tag name of a commit> -f <another commits id> ........to force the tag to another commit

git reflog    .................to se logs of command



**Windows Comand prompt comands:-**

ASSOC          Displays or modifies file extension associations.
ATTRIB         Displays or changes file attributes.
BREAK          Sets or clears extended CTRL+C checking.
BCDEDIT        Sets properties in boot database to control boot loading.
CACLS          Displays or modifies access control lists (ACLs) of files.
CALL           Calls one batch program from another.
CD             Displays the name of or changes the current directory.
CHCP           Displays or sets the active code page number.
CHDIR          Displays the name of or changes the current directory.
CHKDSK         Checks a disk and displays a status report.
CHKNTFS        Displays or modifies the checking of disk at boot time.
CLS            Clears the screen.
CMD            Starts a new instance of the Windows command interpreter.
COLOR          Sets the default console foreground and background colors.
COMP           Compares the contents of two files or sets of files.
COMPACT        Displays or alters the compression of files on NTFS partitions.
CONVERT        Converts FAT volumes to NTFS.  You cannot convert the
               current drive.
COPY           Copies one or more files to another location.
DATE           Displays or sets the date.
DEL            Deletes one or more files.
DIR            Displays a list of files and subdirectories in a directory.
DISKPART       Displays or configures Disk Partition properties.
DOSKEY         Edits command lines, recalls Windows commands, and
               creates macros.
DRIVERQUERY    Displays current device driver status and properties.
ECHO           Displays messages, or turns command echoing on or off.
ENDLOCAL       Ends localization of environment changes in a batch file.
ERASE          Deletes one or more files.
EXIT           Quits the CMD.EXE program (command interpreter).
FC             Compares two files or sets of files, and displays the
               differences between them.
FIND           Searches for a text string in a file or files.
FINDSTR        Searches for strings in files.
FOR            Runs a specified command for each file in a set of files.
FORMAT         Formats a disk for use with Windows.
FSUTIL         Displays or configures the file system properties.
FTYPE          Displays or modifies file types used in file extension
               associations.
GOTO           Directs the Windows command interpreter to a labeled line in
               a batch program.
GPRESULT       Displays Group Policy information for machine or user.
GRAFTABL       Enables Windows to display an extended character set in
               graphics mode.
HELP           Provides Help information for Windows commands.
ICACLS         Display, modify, backup, or restore ACLs for files and
               directories.
IF             Performs conditional processing in batch programs.
LABEL          Creates, changes, or deletes the volume label of a disk.
MD             Creates a directory.
MKDIR          Creates a directory.
MKLINK         Creates Symbolic Links and Hard Links
MODE           Configures a system device.
MORE           Displays output one screen at a time.
MOVE           Moves one or more files from one directory to another
               directory.
OPENFILES      Displays files opened by remote users for a file share.
PATH           Displays or sets a search path for executable files.
PAUSE          Suspends processing of a batch file and displays a message.
POPD           Restores the previous value of the current directory saved by
               PUSHD.
PRINT          Prints a text file.
PROMPT         Changes the Windows command prompt.
PUSHD          Saves the current directory then changes it.
RD             Removes a directory.
RECOVER        Recovers readable information from a bad or defective disk.
REM            Records comments (remarks) in batch files or CONFIG.SYS.
REN            Renames a file or files.
RENAME         Renames a file or files.
REPLACE        Replaces files.
RMDIR          Removes a directory.
ROBOCOPY       Advanced utility to copy files and directory trees
SET            Displays, sets, or removes Windows environment variables.
SETLOCAL       Begins localization of environment changes in a batch file.
SC             Displays or configures services (background processes).
SCHTASKS       Schedules commands and programs to run on a computer.
SHIFT          Shifts the position of replaceable parameters in batch files.
SHUTDOWN       Allows proper local or remote shutdown of machine.
SORT           Sorts input.
START          Starts a separate window to run a specified program or command.
SUBST          Associates a path with a drive letter.
SYSTEMINFO     Displays machine specific properties and configuration.
TASKLIST       Displays all currently running tasks including services.
TASKKILL       Kill or stop a running process or application.
TIME           Displays or sets the system time.
TITLE          Sets the window title for a CMD.EXE session.
TREE           Graphically displays the directory structure of a drive or
               path.
TYPE           Displays the contents of a text file.
VER            Displays the Windows version.
VERIFY         Tells Windows whether to verify that your files are written
               correctly to a disk.
VOL            Displays a disk volume label and serial number.
XCOPY          Copies files and directory trees.
WMIC           Displays WMI information inside interactive command shell.

**permission Metrics Statement**

{
    "Version": "2012-10-17",
    "Statement": [
        {
            "Sid": "VisualEditor0",
            "Effect": "Allow",
            "Action": [
                "iam:GetRole",
                "iam:UpdateAssumeRolePolicy",
                "iam:GetPolicyVersion",
                "iam:ListRoleTags",
                "iam:GetPolicy",
                "iam:PutRolePermissionsBoundary",
                "iam:DeletePolicy",
                "iam:CreateRole",
                "iam:DeleteRole",
                "iam:AttachRolePolicy",
                "iam:PutRolePolicy",
                "iam:DeleteRolePermissionsBoundary",
                "iam:CreatePolicy",
                "iam:DetachRolePolicy",
                "iam:ListPolicyVersions",
                "iam:ListAttachedRolePolicies",
                "iam:DeleteRolePolicy",
                "iam:UpdateRole",
                "iam:ListPolicyTags",
                "iam:CreatePolicyVersion",
                "iam:ListRolePolicies",
                "iam:GetRolePolicy",
                "iam:DeletePolicyVersion"
            ],
            "Resource": [
                "arn:aws:iam::873693938287:role/*",
                "arn:aws:iam::873693938287:policy/*"
            ]
        },
        {
            "Sid": "VisualEditor1",
            "Effect": "Allow",
            "Action": [
                "iam:ListPolicies",
                "iam:ListRoles"
            ],
            "Resource": "*"
        }
    ]
}




**Adding COGNITO As OpenId SSO To Another Cognito**

##Parrent Project Configuration


C:\Users\2195410\LearningProject\cognitoHostedUiwithSSO\parrent-project>amplify add auth
Using service: Cognito, provided by: awscloudformation

 The current configured provider is Amazon Cognito.

 Do you want to use the default authentication and security configuration? Manual configuration
 Select the authentication/authorization services that you want to use: User Sign-Up, Sign-In, connected with AWS IAM controls (Enables per-user Storage features for images or other content, Analytics, and more)


 Provide a friendly name for your resource that will be used to label this category in the project: parrentproject3485b2c93485b2c9
 Enter a name for your identity pool. parrentproject3485b2c9_identitypool_3485b2c9
 Allow unauthenticated logins? (Provides scoped down permissions that you can control via AWS IAM) No
 Do you want to enable 3rd party authentication providers in your identity pool? No
 Provide a name for your user pool: parrentproject3485b2c9_userpool_3485b2c9
 Warning: you will not be able to edit these selections.
 How do you want users to be able to sign in? Email
 Do you want to add User Pool Groups? No
 Do you want to add an admin queries API? No
 Multifactor authentication (MFA) user login options: OFF
 Email based user registration/forgot password: Enabled (Requires per-user email entry at registration)
 Specify an email verification subject: Your verification code
 Specify an email verification message: Your verification code is {####}
 Do you want to override the default password policy for this User Pool? No
 Warning: you will not be able to edit these selections.
 What attributes are required for signing up? Email, Phone Number (This attribute is not supported by Facebook, Login With Amazon, Sign in with Apple.)
 Specify the app's refresh token expiration period (in days): 1
 Do you want to specify the user attributes this app can read and write? No
 Do you want to enable any of the following capabilities?
 Do you want to use an OAuth flow? Yes
 What domain name prefix do you want to use? paraent-pool
 Enter your redirect signin URI: http://localhost:3000/
? Do you want to add another redirect signin URI No
 Enter your redirect signout URI: http://localhost:3000/
? Do you want to add another redirect signout URI No
 Select the OAuth flows enabled for this project. Authorization code grant
 Select the OAuth scopes enabled for this project. OpenID
 Select the social providers you want to configure for your user pool:
? Do you want to configure Lambda Triggers for Cognito? No
✅ Successfully added auth resource parrentproject3485b2c93485b2c9 locally

✅ Some next steps:
"amplify push" will build all your local backend resources and provision it in the cloud
"amplify publish" will build all your local backend and frontend resources (if you have hosting category added) and provision it in the cloud


#override file of auth

 var CHILD_USERPOOL_ID = "ap-south-1_IQE2BH0ti";
    var REGION = "ap-south-1";
    var CHILD_USERPOOL_WEB_CLIENT_ID = "3qr2tnlbi0f95bilna846r5jlb";
    var CHILD_USERPOOL_WEB_CLIENT_SECREAT = "142iiajq307umm9fjf9i1pvps0dtpl0i7rngph0bt68u064gsvjq";
    var CHILD_USERPOOL_DOMAIN_URL = "https://child-pool-dev.auth.ap-south-1.amazoncognito.com"
    var PROVIDER_NAME = "child-pool"


    resources.addCfnResource({
        type : "AWS::Cognito::UserPoolIdentityProvider",
        properties : {
            "AttributeMapping" : {
                "email": "email",
                "email_verified":"email_verified",
                "phone_number":"phone_number",
                "phone_number_verified":"phone_number_verified",
                // "sub":"userName"
              },
            // "IdpIdentifiers" : [ "String" ],
            "ProviderDetails" : {
                "client_id": `${CHILD_USERPOOL_WEB_CLIENT_ID}`,
                "client_secret": `${CHILD_USERPOOL_WEB_CLIENT_SECREAT}`,
                "attributes_request_method":"GET",
                "oidc_issuer":`https://cognito-idp.${REGION}.amazonaws.com/${CHILD_USERPOOL_ID}`,
                "authorize_scopes": "openid",
                "authorize_url":`${CHILD_USERPOOL_DOMAIN_URL}/oauth2/authorize`,
                "token_url":`${CHILD_USERPOOL_DOMAIN_URL}/oauth2/token`,
                "attributes_url":`${CHILD_USERPOOL_DOMAIN_URL}/oauth2/userInfo`,
                "jwks_uri":`https://cognito-idp.${REGION}.amazonaws.com/${CHILD_USERPOOL_ID}/.well-known/jwks.json`
              },
            "ProviderName" : `${PROVIDER_NAME}`,
            "ProviderType" : "OIDC",
            "UserPoolId" : {
                "Ref": "UserPool"
              }
          }
      },"childUserPoolIdentityProvider");

##userpool client needs to be updated with supported identity provider and call back urls
//   resources.userPoolClientWeb.supportedIdentityProviders = [ "COGNITO", `${PROVIDER_NAME}` ];


#child project configuration


Using service: Cognito, provided by: awscloudformation

 The current configured provider is Amazon Cognito.

 Do you want to use the default authentication and security configuration? Manual configuration
 Select the authentication/authorization services that you want to use: User Sign-Up & Sign-In only (Best used with a cloud API only)
 Provide a friendly name for your resource that will be used to label this category in the project: childuserpool
 Provide a name for your user pool: childrenprojectda6f690b_userpool_da6f690b
 Warning: you will not be able to edit these selections.
 How do you want users to be able to sign in? Email
 Do you want to add User Pool Groups? No
 Do you want to add an admin queries API? No
 Multifactor authentication (MFA) user login options: OFF
 Email based user registration/forgot password: Enabled (Requires per-user email entry at registration)
 Specify an email verification subject: Your verification code
 Specify an email verification message: Your verification code is {####}
 Do you want to override the default password policy for this User Pool? No
 Warning: you will not be able to edit these selections.
 What attributes are required for signing up? Email, Phone Number (This attribute is not supported by Facebook, Login With Amazon, Sign in with Apple.)
 Specify the app's refresh token expiration period (in days): 1
 Do you want to specify the user attributes this app can read and write? No
 Do you want to enable any of the following capabilities?
 Do you want to use an OAuth flow? Yes
 What domain name prefix do you want to use? child-pool
 Enter your redirect signin URI: https://${PARRENT_USER_POOL_DOMAIN}/oauth2/idpresponse
? Do you want to add another redirect signin URI No
 Enter your redirect signout URI: http://localhost:3001/
? Do you want to add another redirect signout URI No
 Select the OAuth flows enabled for this project. Authorization code grant
 Select the OAuth scopes enabled for this project. OpenID
 Select the social providers you want to configure for your user pool:
? Do you want to configure Lambda Triggers for Cognito? No
✅ Successfully added auth resource childuserpool locally

✅ Some next steps:
"amplify push" will build all your local backend resources and provision it in the cloud
"amplify publish" will build all your local backend and frontend resources (if you have hosting category added) and provision it in the cloud


# To get the open id configuration of cognito user pool
 https://cognito-idp.${REGION}.amazonaws.com/${CHILD_USER_POOL_ID}/.well-known/openid-configuration


{"authorization_endpoint":"https://child-pool-dev.auth.ap-south-1.amazoncognito.com/oauth2/authorize",
"id_token_signing_alg_values_supported":["RS256"],
"issuer":"https://cognito-idp.ap-south-1.amazonaws.com/ap-south-1_IQE2BH0ti",
"jwks_uri":"https://cognito-idp.ap-south-1.amazonaws.com/ap-south-1_IQE2BH0ti/.well-known/jwks.json",
"response_types_supported":["code","token"],
"scopes_supported":["openid","email","phone","profile"],
"subject_types_supported":["public"],
"token_endpoint":"https://child-pool-dev.auth.ap-south-1.amazoncognito.com/oauth2/token",
"token_endpoint_auth_methods_supported":["client_secret_basic","client_secret_post"],
"userinfo_endpoint":"https://child-pool-dev.auth.ap-south-1.amazoncognito.com/oauth2/userInfo"}

#call Back Url for Child Pool
https://paraent-pool-dev.auth.ap-south-1.amazoncognito.com/oauth2/idpresponse


#override auth
resources.userPoolClientWeb.generateSecret = true;