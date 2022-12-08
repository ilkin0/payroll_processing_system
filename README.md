# Payroll Process System

A simple payroll processing system with minimal features. Users can upload either CSV or TXT file to start
processing

## Installation

The application is containerized, just having docker is enough.
To start the application following command is required:

```bash 
docker compose up
``` 

or

```bash 
docker compose -f docker-compose.yml up
```

## API Reference

#### Process file

```http
  POST /process
```

| Parameter | Type   | Description                   |
|:----------|:-------|:------------------------------|
| `file`    | `data` | **Required**. csv or txt file |

## Usage/Examples

The application does not store the data. Data will be available until the application shut down.
Application output is various reports, which will be available on the application console, such as:
A total number of employees in an organization, Monthly salary report, Yearly financial report, etc.

Example files are also included and can be found in the main folder (eg, Employee_details.txt and Employee_details.csv).
By default, **9090** port will be exposed and POST with 'file' is start the request.

Postman example:
![Postman example](https://i.imgur.com/1Np9tfh.png)