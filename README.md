In this project, we will build a Spring Boot application to serve as the backend for the 'Wordly Week' application. 'Wordly Week' brings together the worlds of writers and magazines, describing the cooperation and collaborations between them. Users can delve into the application to identify writers who've left their mark on the magazines and discover the range of magazines a single writer has written.

For this application, our main entities are `Writer` and `Magazine`. The `Writer` entity is in a Many-to-Many relationship with the `Magazine` entity.

<details>
<summary>**Implementation Files**</summary>

Use these files to complete the implementation:

- `WriterController.java`
- `WriterRepository.java`
- `WriterJpaService.java`
- `WriterJpaRepository.java`
- `Writer.java`
- `MagazineController.java`
- `MagazineRepository.java`
- `MagazineJpaService.java`
- `MagazineJpaRepository.java`
- `Magazine.java`

</details>

Create a database that contains three tables `writer`, `magazine`, and `writer_magazine` using the given database schema.

You can refer to this [session](https://learning.ccbp.in/course?c_id=e345dfa4-f5ce-406e-b19a-4ed720c54136&s_id=6a60610e-79c2-4e15-b675-45ddbd9bbe82&t_id=f880166e-2f51-4403-81a0-d2430694dae8), for creating a database.

_Create the SQL files and compose accurate queries to run the application. Inaccurate SQL files will result in test case failures._

<details>
<summary>**Database Schema**</summary>

#### Writer Table

| Columns |                 Type                  |
| :-----: | :-----------------------------------: |
|   id    | INTEGER (Primary Key, Auto Increment) |
|  name   |                 TEXT                  |
|   bio   |                 TEXT                  |

#### Magazine Table

|     Columns     |                 Type                  |
| :-------------: | :-----------------------------------: |
|       id        | INTEGER (Primary Key, Auto Increment) |
|      title      |                 TEXT                  |
| publicationDate |                 TEXT                  |

#### Junction Table

|  Columns   |                Type                |
| :--------: | :--------------------------------: |
|  writerId  | INTEGER (Foreign Key) |
| magazineId | INTEGER (Foreign Key) |

The columns `writerId` and `magazineId` can be combinedly declared as Primary Keys.

You can use the given sample data to populate the tables.

<details>
<summary>**Sample Data**</summary>

#### Writer Data

|  id   |       name       |                                   bio                                   |
| :---: | :--------------: | :---------------------------------------------------------------------: |
|   1   |     John Doe     |                     Famous writer of fantasy tales                      |
|   2   |    Jane Smith    |                     Renowned journalist and editor                      |
|   3   |   Emily Brontë   |                       Author of Wuthering Heights                       |
|   4   | Ernest Hemingway | Nobel Prize-winning author known for works like The Old Man and the Sea |

#### Magazine Data

|  id   |           title            | publicationDate |
| :---: | :------------------------: | :-------------: |
|   1   |       Fantasy Tales        |   2023-10-05    |
|   2   |     Journalist Weekly      |   2023-09-15    |
|   3   | Classic Literature Monthly |   2023-10-15    |
|   4   |   Modern Writers Digest    |   2023-09-20    |

#### Junction Table

| writerId | magazineId |
| :------: | :--------: |
|    1     |     1      |
|    1     |     2      |
|    2     |     2      |
|    3     |     3      |
|    4     |     3      |
|    4     |     4      |

</details>

</details>

<MultiLineNote>

Use only `writer`, `magazine`, and `writer_magazine` as the table names in your code.

</MultiLineNote>

### Completion Instructions

- `Writer.java`: The `Writer` class should contain the following attributes.

    | Attribute  |      Type      |
    | :--------: | :------------: |
    |  writerId  |      int       |
    | writerName |     String     |
    |    bio     |     String     |
    | magazines  | List\<Magazine> |

- `WriterRepository.java`: Create an `interface` containing the required methods.
- `WriterJpaService.java`: Update the service class with logic for managing writer data.
- `WriterController.java`: Create the controller class to handle HTTP requests.
- `WriterJpaRepository.java`: Create an interface that implements the `JpaRepository` interface.
  
- `Magazine.java`: The `Magazine` class should contain the following attributes.

    |    Attribute    |     Type     |
    | :-------------: | :----------: |
    |   magazineId    |     int      |
    |  magazineName   |    String    |
    | publicationDate |    String    |
    |     writers     | List\<Writer> |

- `MagazineRepository.java`: Create an `interface` containing the required methods.
- `MagazineJpaService.java`: Update the service class with logic for managing magazine data.
- `MagazineController.java`: Create the controller class to handle HTTP requests.
- `MagazineJpaRepository.java`: Create an interface that implements the `JpaRepository` interface.

Implement the following APIs.

<details>
<summary>**API 1: GET /magazines**</summary>

#### Path: `/magazines`

#### Method: `GET`

#### Description:

Returns a list of all magazines in the `magazine` table.

#### Response

```json
[
    {
        "magazineId": 1,
        "magazineName": "Fantasy Tales",
        "publicationDate": "2023-10-05",
        "writers": [
            {
                "writerId": 1,
                "writerName": "John Doe",
                "bio": "Famous writer of fantasy tales"
            }
        ]
    },
    ...
]
```

</details>

<details>
<summary>**API 2: POST /magazines**</summary>

#### Path: `/magazines`

#### Method: `POST`

#### Description:

Creates a new magazine in the `magazine` table. Also, create an association between the magazine and writers in the `writer_magazine` table based on the `writerId`s provided in the `writers` field. The `magazineId` is auto-incremented.

#### Request

```json
{
    "magazineName": "Mystery Weekly",
    "publicationDate": "2023-11-01",
    "writers": [
        {
            "writerId": 4
        }
    ]
}
```

#### Response

```json
{
    "magazineId": 5,
    "magazineName": "Mystery Weekly",
    "publicationDate": "2023-11-01",
    "writers": [
        {
            "writerId": 4,
            "writerName": "Ernest Hemingway",
            "bio": "Nobel Prize-winning author known for works like The Old Man and the Sea"
        }
    ]
}
```

</details>

<details>
<summary>**API 3: GET /magazines/{magazineId}**</summary>

#### Path: `/magazines/{magazineId}`

#### Method: `GET`

#### Description:

Returns a magazine based on the `magazineId`. If the given `magazineId` is not found in the `magazine` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
{
    "magazineId": 1,
    "magazineName": "Fantasy Tales",
    "publicationDate": "2023-10-05",
    "writers": [
        {
            "writerId": 1,
            "writerName": "John Doe",
            "bio": "Famous writer of fantasy tales"
        }
    ]
}
```

</details>

<details>
<summary>**API 4: PUT /magazines/{magazineId}**</summary>

#### Path: `/magazines/{magazineId}`

#### Method: `PUT`

#### Description:

Updates the details of a magazine based on the `magazineId` and returns the updated magazine details. Also update the associations between the magazine and writers, if the `writers` field is provided. If the given `magazineId` is not found in the `magazine` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Request

```json
{
    "magazineName": "The Mystery Weekly",
    "writers": []
}
```

#### Success Response

```json
{
    "magazineId": 5,
    "magazineName": "The Mystery Weekly",
    "publicationDate": "2023-11-01",
    "writers": []
}
```

</details>

<details>
<summary>**API 5: DELETE /magazines/{magazineId}**</summary>

#### Path: `/magazines/{magazineId}`

#### Method: `DELETE`

#### Description:

Deletes a magazine from the `magazine` table and its associations from the `writer_magazine` table based on the `magazineId` and returns the status code `204`(raise `ResponseStatusException` with `HttpStatus.NO_CONTENT`). If the given `magazineId` is not found in the `magazine` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

</details>

<details>
<summary>**API 6: GET /magazines/{magazineId}/writers**</summary>

#### Path: `/magazines/{magazineId}/writers`

#### Method: `GET`

#### Description:

Returns all writers associated with the magazine based on the `magazineId`. If the given `magazineId` is not found in the `magazine` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
[
    {
        "writerId": 1,
        "writerName": "John Doe",
        "bio": "Famous writer of fantasy tales",
        "magazines": [
            {
                "magazineId": 1,
                "magazineName": "Fantasy Tales",
                "publicationDate": "2023-10-05"
            },
            {
                "magazineId": 2,
                "magazineName": "Journalist Weekly",
                "publicationDate": "2023-09-15"
            }
        ]
    }
]
```

</details>

<details>
<summary>**API 7: GET /magazines/writers**</summary>

#### Path: `/magazines/writers`

#### Method: `GET`

#### Description:

Returns a list of all writers in the `writer` table.

#### Response

```json
[
    {
        "writerId": 1,
        "writerName": "John Doe",
        "bio": "Famous writer of fantasy tales",
        "magazines": [
            {
                "magazineId": 1,
                "magazineName": "Fantasy Tales",
                "publicationDate": "2023-10-05"
            },
            {
                "magazineId": 2,
                "magazineName": "Journalist Weekly",
                "publicationDate": "2023-09-15"
            }
        ]
    },
    ...
]
```

</details>

<details>
<summary>**API 8: POST /magazines/writers**</summary>

#### Path: `/magazines/writers`

#### Method: `POST`

#### Description:

Creates a new writer in the `writer` table, if all the `magazineId`s in the `magazines` field exist in the `magazine` table. Also, create an association between the writer and magazines in the `writer_magazine` table. The `writerId` is auto-incremented. If any given `magazineId` is not found in the `magazine` table, raise `ResponseStatusException` with `HttpStatus.BAD_REQUEST`.

#### Request

```json
{
    "writerName": "Agatha Christie",
    "bio": "Best-selling mystery writer known for works like Murder on the Orient Express",
    "magazines": [
        {
            "magazineId": 4
        }
    ]
}
```

#### Success Response

```json
{
    "writerId": 5,
    "writerName": "Agatha Christie",
    "bio": "Best-selling mystery writer known for works like Murder on the Orient Express",
    "magazines": [
        {
            "magazineId": 4,
            "magazineName": "Modern Writers Digest",
            "publicationDate": "2023-09-20"
        }
    ]
}
```

</details>

<details>
<summary>**API 9: GET /magazines/writers/{writerId}**</summary>

#### Path: `/magazines/writers/{writerId}`

#### Method: `GET`

#### Description:

Returns a writer based on the `writerId`. If the given `writerId` is not found in the `writer` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.


#### Success Response

```json
{
    "writerId": 1,
    "writerName": "John Doe",
    "bio": "Famous writer of fantasy tales",
    "magazines": [
        {
            "magazineId": 1,
            "magazineName": "Fantasy Tales",
            "publicationDate": "2023-10-05"
        },
        {
            "magazineId": 2,
            "magazineName": "Journalist Weekly",
            "publicationDate": "2023-09-15"
        }
    ]
}
```

</details>

<details>
<summary>**API 10: PUT /magazines/writers/{writerId}**</summary>

#### Path: `/magazines/writers/{writerId}`

#### Method: `PUT`

#### Description:

Updates the details of a writer based on the `writerId` and returns the updated writer details. Also update the associations between the writer and magazines, if the `magazines` field is provided. If the given `writerId` is not found in the `writer` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`. If any given `magazineId` is not found in the `magazine` table, raise `ResponseStatusException` with `HttpStatus.BAD_REQUEST`.

#### Request

```json
{
    "bio": "Best-selling mystery writer known for works like Murder on the 'Orient Express'",
    "magazines": [
        {
            "magazineId": 5
        }
    ]
}
```

#### Success Response

```json
{
    "writerId": 5,
    "writerName": "Agatha Christie",
    "bio": "Best-selling mystery writer known for works like Murder on the 'Orient Express'",
    "magazines": [
        {
            "magazineId": 5,
            "magazineName": "The Mystery Weekly",
            "publicationDate": "2023-11-01"
        }
    ]
}
```

</details>

<details>
<summary>**API 11: DELETE /magazines/writers/{writerId}**</summary>

#### Path:  `/magazines/writers/{writerId}`

#### Method: `DELETE`

#### Description:

Deletes a writer from the `writer` table and its associations from the `writer_magazine` table based on the `writerId` and returns the status code `204`(raise `ResponseStatusException` with `HttpStatus.NO_CONTENT`). If the given `writerId` is not found in the `writer` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

</details>

<details>
<summary>**API 12: GET /writers/{writerId}/magazines**</summary>

#### Path: `/writers/{writerId}/magazines`

#### Method: `GET`

#### Description:

Returns all magazines associated with the writer based on the `writerId`. If the given `writerId` is not found in the `writer` table, raise `ResponseStatusException` with `HttpStatus.NOT_FOUND`.

#### Success Response

```json
[
    {
        "magazineId": 1,
        "magazineName": "Fantasy Tales",
        "publicationDate": "2023-10-05",
        "writers": [
            {
                "writerId": 1,
                "writerName": "John Doe",
                "bio": "Famous writer of fantasy tales"
            }
        ]
    },
    {
        "magazineId": 2,
        "magazineName": "Journalist Weekly",
        "publicationDate": "2023-09-15",
        "writers": [
            {
                "writerId": 1,
                "writerName": "John Doe",
                "bio": "Famous writer of fantasy tales"
            },
            {
                "writerId": 2,
                "writerName": "Jane Smith",
                "bio": "Renowned journalist and editor"
            }
        ]
    }
]
```

</details>

**Do not modify the code in `WordlyWeekApplication.java`**