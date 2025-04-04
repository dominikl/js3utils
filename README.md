# JS3Utils

Some Java S3 command line utilities. Work in progress...

Currently implemented:
- Download a folder from a public S3-compatible storage

## Usage:

Download an ome.zarr folder from a public S3-compatible storage:

```bash
./js3utils download https://uk1s3.embassy.ebi.ac.uk/idr/zarr/v0.4/idr0062A/6001240.zarr
```

## Requirements
- Java 11 or higher

## Build Requirements
- Gradle

## Building
```bash
./gradlew build
```

## Running
```bash
./gradlew run
```

## Testing
```bash
./gradlew test
```
