# GTD project

This repository contains the source code for the GTD backend part.

Expose REST API with Spring for data persistence (Hibernate) into database.

Have a questions about gtd? Ask away on the on email drugnanov84@gmail.com.

## HOW TO

Learn how to develop with IntelliJ and Gradle.

## Configuration

Change for your database instance:<br/>
resources/hibernate.cfg<br/>
example:<br/>
<pre>
<property name="hibernate.connection.url">jdbc:mysql://localhost:3306/gtd_database</property>
<property name="hibernate.connection.username">MichalSlama</property>
<property name="hibernate.connection.password">heslo</property>
</pre>

## License

* [Apache Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.html)


Copyright 2014 Michal Sláma<br/>
Copyright 2014 GitHub Inc.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.


## Building

The build requires [Gradle](http://www.gradle.org/downloads) v2.10+.

After satisfying those requirements, the build is pretty simple:

* Run `gradle bootRepackage` from the `app` directory to build the war into build\libs


## Acknowledgements

GTD project is created as BP school project on ČVUT FIT.

Main framework si common known Spring with Spring boot extension and for DAO its Hibernate.

* [SpringBoot](http://projects.spring.io/spring-boot/c) is able to make stan-alone spring application.
* [Hibernate](http://hibernate.org/)

I hope this helps you in building your next android app.
