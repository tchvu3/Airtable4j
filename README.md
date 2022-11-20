<p align="center">
  <img width="300" height="300" src="https://github.com/tchvu3/Airtable4j/blob/master/src/main/resources/company-logo.png">
</p>

> :warning: **This project is under active development, while the API is stable some bugs might still be present.**

[![License: MIT](https://img.shields.io/badge/License-MIT-brightgreen.svg)](https://opensource.org/licenses/MIT)
![Repo Size](https://img.shields.io/github/repo-size/tchvu3/Airtable4j)
[![FOSSA Status](https://app.fossa.com/api/projects/git%2Bgithub.com%2Ftchvu3%2FAirtable4j.svg?type=shield)](https://app.fossa.com/projects/git%2Bgithub.com%2Ftchvu3%2FAirtable4j?ref=badge_shield)

[//]: # ([![Downloads]&#40;https://pepy.tech/badge/global-chem&#41;]&#40;https://pepy.tech/project/global-chem&#41;)

# Airtable4j - Java based Airtable API

This library has been designed to be as simple and intuitive as possible to use.

most of the usage can be inferred by auto-completing your way though the API.

## Install
add the following dependency to your maven project:
```
COMING SOON!
```

or to your gradle project:
```
COMING SOON!
```

## Usage

### Getting an instance of Airtable:

#### pointing to specific table in a specific database
```
Airtable.PublicApi airtablePublicApi = Airtable.buildInstance("API_KEY", "DATABASE", "TABLE");
```

#### pointing to the whole database, you'll need to provide the database and the table names with each request
```
Airtable.CustomPublicApi airtableCustomPublicApi = Airtable.buildInstance("API_KEY");
```

after getting reference to your database,
you have access to the following operations:

```
airtablePublicApi.add();
airtablePublicApi.replace();
airtablePublicApi.delete();
airtablePublicApi.update();
airtablePublicApi.get();
airtablePublicApi.getSingle();
```

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

note that you can pass any type of argument to generic functions (like 'add'):

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
