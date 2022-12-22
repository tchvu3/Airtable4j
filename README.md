<p align="center">
  <img width="300" height="300" src="https://github.com/tchvu3/Airtable4j/blob/master/src/main/resources/company-logo.png">
</p>

[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT)
[![Repo Size](https://img.shields.io/github/repo-size/tchvu3/Airtable4j)](https://img.shields.io/github/repo-size/tchvu3/Airtable4j)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Ftchvu3%2FAirtable4j.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Ftchvu3%2FAirtable4j?ref=badge_shield)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/0a94e3d6a749415cb7feb9826cef0ae0)](https://www.codacy.com/gh/tchvu3/Airtable4j/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=tchvu3/Airtable4j&amp;utm_campaign=Badge_Grade)

# Airtable4j - Java based Airtable API

This library has been designed to be as simple and intuitive as possible to use.

most of the usage can be inferred by auto-completing your way though the API.

## Install

add the following dependency to your maven project:

```
<dependency>
    <groupId>com.avihu-harush</groupId>
    <artifactId>airtable4j</artifactId>
    <version>3.1.3</version>
</dependency>
```

or to your gradle project:

```
implementation 'com.avihu-harush:airtable4j:3.1.3'
```

## Usage

### Getting an instance of Airtable

You have 2 ways of getting a reference to your Airtable data, by pointing to the root or a specific table.

#### Pointing to specific table in a specific database

```
Airtable.PublicApi airtablePublicApi = Airtable.buildInstance("API_KEY", "DATABASE", "TABLE");
```

#### Pointing to the whole database

```
Airtable.CustomPublicApi airtableCustomPublicApi = Airtable.buildInstance("API_KEY");
```

#### Setting API key through external variable

You can provide the Airtable API key as a system property or environment variable.
simply set a variable name `AIRTABLE4J_API_KEY` and instantiate Airtable like so:

```
Airtable.CustomPublicApi airtableCustomPublicApi = Airtable.buildInstance();
```

note that if you'll try to instantiate Airtable like
that and not provide the required API key an exception will be thrown.

#### Customizing the Airtable API endpoint

If you need to change the default Airtable API endpoint URL
you can set a system property or environment variable named `AIRTABLE4J_BASE_URL`
and set it to the required URL.

#### Base operation

after getting reference to your database
you'll have access to the following operations:

- `add()`
- `replace()`
- `delete()`
- `update()`
- `get()`
- `getSingle()`

The API is chainable, so you can query for data like:

```
// most basic form of querying, asking for everything and getting generic response
AirtableGetResponse result1 = airtablePublicApi.get().execute();

// you can pass class to the 'execute' method to get the result as a specific class
List<Person> result2 = airtablePublicApi.get().execute(Person.class);

// you might add additional constrains (only part of the possible constrains are presented in this example)
List<Person> result3 = airtablePublicApi.get()
                .setMaxRecords(5)
                .setFilterByFields("name", "age")
                .setSortByFields(AirtableSortField.ascending("name"))
                .execute(Person.class);
                
// if you want an iterator and not directly query for everything you might use the 'iterate' method
// NOTE: the iterator is lazy! so additional network requests will be made when the local data is depleted
AirtableGetIterator<Person> iterate = airtablePublicApi.get().iterate(Person.class);

// you might iterate regulary like so
for (Person person : iterate) {
  System.out.println(person);
}

// or ask for everything from the iterator
List<Person> iterateAll = iterate.getAll();
```

note that you can pass any type of argument to generic functions (like `add`):

```
// this will be valid
airtablePublicApi.add().addRecords(new Person(), "TEXT", 12);
```

or you can strong type the method and let the compiler type-check your code:

```
// this will NOT be valid and will lead to compiliation error.
// String and int are incompatible with the Person class
airtablePublicApi.<Person>add().addRecords(new Person(), "TEXT", 12);
```

#### Predefined Airtable constructs

you can use custom classes for specific Airtable constructs like `Attachment` or `Barcode`.
every available custom field class:

- `AirtableAttachment`
- `AirtableBarcode`
- `AirtableButton`
- `AirtableCollaborator`

#### Different names for Airtable fields and java class fields

if some Airtable table fields having different names to your
local java class fields you can use `AirtableField` annotation to declare that:

```
public class Person {
  @AirtableField("full_name") // <- the name in Airtable
  private String fullName; // <- regular java class field
}
```
