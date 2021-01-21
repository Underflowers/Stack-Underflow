

<p align="center">
<img src="doc/logo.png">
</p>

<p align="center">
  <img src="https://forthebadge.com/images/badges/built-with-love.svg" alt="version">
  <img src="https://forthebadge.com/images/badges/made-with-java.svg" alt="platform">
  <img src="https://forthebadge.com/images/badges/ctrl-c-ctrl-v.svg" alt="platform">
  <img src="https://forthebadge.com/images/badges/powered-by-black-magic.svg" alt="platform">
</p>

<p align="center">
  <img src="https://github.com/Underflowers/Stack-Underflow/workflows/Tests/badge.svg?branch=develop" alt="Tests">
</p>

## About

Stack underflow is a <s>Chinese</s> clone of [stack overflow](https://stackoverflow.com/). This project was developed at the [HEIG-VD](https://heig-vd.ch/) as part of the AMT course. 

## Deployment

### Underification

This project uses the gamification engine [Underification](https://github.com/Underflowers/Underification/). Just make sure that you have a version of it running somewhere, you can follow the deployment instruction [here](https://github.com/Underflowers/Stack-Underflow#deployment).

### Configuration

If you've cloned our repository, juste copy our `server.env` example file:

```bash
$ cp src/main/liberty/config/server.env.example src/main/liberty/config/server.env
```

Then you'll need to register the application in the Underification engine. This is quite straightforward, just run the `init.sh` script (you can find it in the `init/gamification/` folder in the repository) and copy the returned token in your `server.env` (`UNDERIFICATION_AUTH_TOKEN` variable).

>Note: If you do not host the gamification engine on `http://localhost:8080` (default deployment), you'll have to export an environment variable `UNDERIFICATION_URL` before running the init script **and** update the `server.env` file.

### Run

We've generated (well..we were forced to) a Docker image that contain the latest version of Stack underflow and [Open Liberty](https://openliberty.io/) as the application server. Additionaly, we provide a compose file which starts up a MySQL database and the application server.

```bash
$ docker-compose up -d
```

> Note: You may want to change the database credentials (we recommend it too), just don't forget to update your `server.env`.
>
> The paths used in the docker-compose.yml are relative to the project structure, but if you don't wont to have all of the project locally, don't forget to update them.

The server should now be running on `localhost:9080`.

## Contributing

### Prerequisites

- Java (v11.0.8)
- Git (v2.28.0)
- Docker (v19.03.12-ce)
- Docker-compose (v1.27.4)
- Maven (v3.6.3)
- Npm (v6.14.7)

### Project setup

Clone the repository, then install the dependencies

```bash
$ npm i
```

> Note: You don't need to install the maven dependencies since it's automatically done when building the archive

Startup the database:

```bash
$ docker-compose up db
```

Now you'll need to setup the database environement variables so Open Liberty can connect to it. Simply copy the example file provided and update it acordingly:

```bash
$ cp src/main/liberty/config/server.env.example src/main/liberty/config/server.env
```

Of course, you'll need the gamification engine here too, have a look at [the deployment section](#Deployment).

#### Backend

To help with development you can use Open Liberty hot reloading server

```bash
$ mvn compile liberty:dev
```

And now you can access the application at `http://localhost:9080`.

#### Frontend

Since this project uses [Tailwindcss](https://tailwindcss.com/), you'll need to make sure to install it (with it's dependencies) in order to change the styling.

> Note: If you don't want to change the styling you can skip this whole section since there is a default stylesheet in the project
>

You then need to run one of the following to compile the css:

```sh
# development
$ npm run watch

# production
$ npm run build
```

And voila!

> Note: You need to redeploy the war to the server in order to see changes

### Tests

#### Unit

Unit tests are using JUnit 5. You can run them all with the mvn command `mvn test`.

#### Integration

For our integration test, we're using [Arquillian](https://arquillian.org/). It helps with not worrying about the database connection and dependency injections.

Before thinking about running the tests, you'll need to create a `src/test/resources/arquillian.xml`. You can simply copy the example file and change the `wlpHome` path.

```bash
$ cp src/test/resources/arquillian.xml.example src/test/resources/arquillian.xml
```

Then you need to make sure the database is running. 

Now, to run the tests, you can simply use  `run-integration-tests.sh`.

#### e2e

For the end to end tests, we're using [CodeceptJS](https://codecept.io/)). 

To run the e2e tests, run the following:

```bash
$ cd e2e/
$ npx codeceptjs run --steps
```

> Note: If don't like that a chromium window opens to run the tests, you can run this instead: 
>
> ```bash
> $ npx codeceptjs run --override '{"helpers": {"Puppeteer": {"show": false}}}' --steps
> ```

## Documentation

Apart from this readme, you can find more documents in the `doc/` folder.

## Main contributors

| Name               | Username      |
| ------------------ | ------------- |
| Robin Demarta      | rdemarta      |
| Loic Dessaules     | gollgot       |
| Thibaud Franchetti | ChatDeBlofeld |
| Doran Kayoumi      | kayoumido     |

