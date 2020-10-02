# AMT\_Project
## Pages

### Common elements

A header and a footer are displayed on all pages.

The header contains multiple links which can be followed by the user:

- A link hidden in the *Stack Underflow* logo. It leads to the [home page](#home-page).
- A `Questions` label leading to the [question list](#all-questions).
- A `Tags` label leading to **WHATEVER**.
- A `Login` label leading to the [login page](#login). This label is only displayed if the user isn't logged in.
- A `Register` label leading to the [register page](#register). This label is only displayed if the user isn't logged in.
- A label displaying the user's identity leading to the [user's profile page](#user-profile). This label is only displayed if the user is logged in.

The footer displays various information about the authors and the legal stuff. It contains the same labels as the header too.

### Home page

Description of the website and also shows some
statistics (nb users, nb of questions, nb of tags)

### Login

Displays a greeting and a form to log in. The page should not be reachable if the user is already logged in.

The user can fill an `email address` and a `password` field in the form. A login request is handled when the form is submitted. The user will be redirected on the page he was browsing before login or in his profile if none.

By clicking on a link labelled `Not registered?`, the user is redirected to the [register page](#register).

### Register

Displays a register form. The page should not be reachable if the user is already registered and logged in.

The user can fill the `First name`, `Last name`, `Email address` and `Password` fields and then submit the form with a `Register` button.

The user can also follow a `Already have an account?` labelled link to directly log in.

### User profile

The profile contains information about an user:

- Full name
- Username
- Profile picture
- Description
- Number of asked questions
- Number of answered questions
- List of recent posts

The user can click on any recent post. If the user owns the profile, he can edit the data.

### All questions

This page displays all the questions. Only **TODO** questions are displayed at the time but the user can use some buttons to see the next or the previous ones. The total number of questions is displayed.

Some information about the questions is displayed. They are the same as the ones described in [question detail](#question-detail) but only a sample of the body is displayed.

The user can also use a button to ask a question.

### Question detail

This page displays some information about the question:

- Title
- Body
- Tags related
- Number of votes
- Number of answers
- Author (can be clicked, leads to a [user's profile](#user-profile))
- Datetime
- A badge if the question is solved

The page also displays all the answers (and the number of them). An answer contains:

- A text body
- An author (can be clicked, leads to a [user's profile](#user-profile))
- A badge if it has solved the associated question
- A number of votes

If the user viewing the page has asked the question, he can edit it. He can also select an answer to notify his question has been solved.

Every logged user can fill a form to send an answer and upvote or downvote the question or any answer.

At the top of the page we can found a "<- All questions" button that redirect the user to the all questions page.

### New question

The user must be logged in to access this page. Then, he can submit a form containing:

- Question title
- Question body
- Tags related

### Optional
#### All tags
#### Search results

## Installation and run (linux)
To install and run the application, you must have docker and a github Personal Access Token. If you already logged into the GHCR with a personal access token that have read:packages rights, go to steps 3.

 1. Generate a Personal Access Token to be able to log into the ghcr (GitHub Container Registry). On your github account, go to Settings -> developer settings -> Personal Access Token -> Generate new token.
 Give a name for your token an select the `read:packages` box. Don't forget to copy your token !
 
 2. You have to connect to the GHCR with this command: `docker login ghcr.io -u <YOUR_GITHUB_USERNAME>` and enter your personal access token as password.
 
 3. Use this command to pull the latest stable version of StackUnderflow: `docker pull ghcr.io/underflowers/stack-underflow:latest` 
 
 4. Use this command to run the container: `docker run ghcr.io/underflowers/stack-underflow:latest`

 5. To access the website, you must have the IP of the container you launched. To do that, run this command `docker inspect CONTAINER_ID`, and copy the container's id (The CONTAINER_ID can be found with `docker ps`).
    
 6. Open your browser and type: `http://CONTAINER_IP:9080/stack-underflow/` to show our app.

## Frontend
Since this project uses [Tailwindcss](https://tailwindcss.com/), you'll need to install it (with it's dependencies) in order to change the styling.
> Note: If you don't want to change the styling you can skip this whole section since a default stylesheet was added to the repo
> 
> The following commands need to be executed at the root of the project.

First install the dependencies:
```
$ npm i
```

You then need to run the following to compile the css:
```sh
# development
$ npm run watch

# production
$ npm run build
```

And voila!
> Note: You need to redeploy the war to the server in order to see changes