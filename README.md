

<p align="center">
<img src="doc/logo.png">
</p>

<p align="center">
  <img src="https://forthebadge.com/images/badges/built-with-love.svg" alt="version">
  <img src="https://forthebadge.com/images/badges/made-with-java.svg" alt="platform">
  <img src="https://forthebadge.com/images/badges/ctrl-c-ctrl-v.svg" alt="platform">
  <img src="https://forthebadge.com/images/badges/powered-by-black-magic.svg" alt="platform">
</p>



## About

Stack underflow is a <s>Chinese</s> clone of [stack overflow](https://stackoverflow.com/). This project was developed at the [HEIG-VD](https://heig-vd.ch/) as part of the AMT course. 

## Deployment

We've generated (well..we were forced to) a Docker image that contain the latest version of Stack underflow and [Open Liberty](https://openliberty.io/) as the application server.

```bash
$ docker pull ghcr.io/underflowers/stackunderflow:latest
```

As is, the application will most likely not work. Why you ask? Well the database isn't packaged in the image we offer. So all you need to do is setup a database yourself. The schema can be found [here](./docker/init/database/schema.sql). Once the you have a db running, you'll need to add an environment file (server.env) with the information needed so that  Open Liberty can establish a connection with it. it should look something like this:

```
DB_HOST=127.0.0.1
DB_PORT=3306
DB_DATABASE=MyDatabase
DB_USERNAME=root
DB_PASSWORD=root
```

> Note: You'll need to place the file in `/config/server.env` on the server

To help with this tedious task, we've created a [docker-compose.yml]() which does everything explained above.

```bash
$ docker-compose up -d
```

> Note: If you decide to use our docker-compose.yml, you'll need to updated it with the database environment variables and most likely adapt the paths to the different files.

## Contributing

### Structure

TODO

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

#### Backend

To help with development you can use Open Liberty hot reloading server

```bash
$ mvn liberty:dev
```

> Note: Make sure you've set the correct 

And now you can access the application at `http://localhost:8080`.

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

