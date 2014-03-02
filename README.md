CDI-inspector
=============

A set of tools for CDI visualization and inspection.

## Eclipse update site

`https://raw.github.com/jakub-n/cdi-inspector-repo/master/repos/cdii`

Update site contains all necessary dependencies.

## Local build

### Requirements

* Maven 3

### Build

* `cd <reporoot>/code/common/model`
* `mvn clean install -Posgi`
* `cd <reporoot>/code/eclipse-plugin`
* `mvn clean install`

### Installation

1. Add *<reporoot>/code/eclipse-plugin/repository/target/repository/* directory as a new local update site in your Eclipse installation.
2. Install all features from this update site.

## Usage

1. Right click on Java EE project in Package Explorer view.
2. Select 'Inspect CDI beans'.

