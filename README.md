# PrologAssistant

This is a free-time passion project to practice my software developement skills.
In essence, this project models a prolog reasoner and simple parsing/syntax - Facilitating an entire prolog system from (regex-based) parsing to a complete reasoner.
My proudest piece is a custom implementation of the unification algorithm that I developed entirely by myself and derived from the classic Robinson unification algorithm.
The project is also supported by comprehensive tests and written in a highly modular and maintainable way.

## Algorithms: Best of

### The List-unification domain system
I came up with this system all by myself! It can unify complex lists consisting of both Variables and other Terms.

#### Generating all possible segmentations of a List consisting of n variables

Let us assume two lists `[X,Y,Z]` and `[dog,cat,mouse]`
The possible unifications are:
``[

## Syntax

To make regex-parsing possible, the traditional prolog syntax has been changed:

### Atoms
Atoms are any lowercase word such as `dog,cat,house`

### Variables
Variables are any upper case word such as  `SALAD,X,Y`
A special type of variable is dont-care, written as `_`

### Relations
Relations are somewhat like atoms, but with a list of different terms
Relations usually look like `related(dog,cat,bird,automobile)`

### Lists
Lists are writte like this: `[term1.terms(dog,cat,mouse).last]`
Note that a punctuation mark is used instead of a comma to ease parsing

