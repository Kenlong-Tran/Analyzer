# Analyzer
Reads in an input of string for the following BNF:
<blck> ::= begin <stmts> end
<stmts> ::= <empty> | <stmt> <stmts>
<stmt> ::= pass | declare <name> | use <name> | <blck>

<blck> will create a new scope, <stmts> can be empty or more than one <stmt>, and <stmt> can pass, declare a variable, use a variable, or create another <blck>. The declared variables will be numbered and the use variable will choose the most recent variable declaration called.

For example the following statement:
begin pass declare x use y declare y begin use x declare x use x declare x use x declare y end use x end

Will create the following:
begin
  pass
  declare x {declaration 1}
  use y {illegal undeclared use}
  declare y {declaration 2}
  begin
    use x {references declaration 1}
    declare x {declaration 3}
    use x {references declaration 3}
    declare x {illegal redeclaration}
    use x {references declaration 3}
    declare y {declaration 4}
  end
  use x {references declaration 1}
end
