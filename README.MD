A simple interpreter made in Java.

It can only run hard coded set of commands.

Programs
  1:
    int v; 
    v = 2; 
    Print(v)
    
  2:
    a=2+3*5;
    b=a+1;
    Print(b)
    
  3:
    bool a; 
    int v;
    a=true;
    If a
      Then v=2
    Else
      v=3;
    Print(v)
    
  a3-2:
    string varf;
    varf="test.in";
    openRFile(varf);
    int varc;
    readFile(varf,varc);
    print(varc);
    readFile(varf,varc);
    print(varc);
    closeRFile(varf);
    
  a4-1:
    Ref int v;
    new(v,20);
    Ref Ref int a;
    new(a,v);
    print(v);
    print(a);
    
  a4-2:
    Ref int v;
    new(v,20);
    Ref Ref int a;
    new(a,v);
    print(rH(v));
    print(rH(rH(a))+5);
    
  a4-3:
    Ref int v;
    new(v,20);
    print(rH(v));
    wH(v,30);
    print(rH(v)+5);
    
  a4-4:
    Ref int v;
    new(v,20);
    Ref Ref int a;
    new(a,v);
    new(v,30);
    print(rH(rH(a)));
    
  a4-5:
    int v;
    v=4;
    while (v>0)
      print(v);
      v=v-1;
    print(v);
    
  a5:
    int v; 
    Ref int a;
    v=10;
    new(a,22);
    fork(wH(a,30);
    v=32;
    print(v);
    print(rH(a)));
    print(v);
    print(rH(a))
    
  T1:
    int v;
    int x;
    int y;
    v=0;
    Repeat 
      fork(print(v); v=v - 1);
      v=v + 1 
    Until v == 3;
    x=1;
    Nop;
    y=3;
    Nop;
    Print(v * 10);
    
Expressions that need to be explained:
  Ref: Reference type. Contains the heap address where the value is kept and the type of the value.
  openRFile(name): Adds the file "name", where name is a string, to the file table. That means that now, the contents of the file can be read by our program (integers only).
  readFile(name, var): Reads a line from the file "name", and puts the value in var.
  closeRFile(name): Closes the file "name".
  new(ref, value): Puts value (which is a value with the same type as the value inside ref, ref being a reference variable) in the heap. The address where "value" is stored is     associated with ref.
  wH(ref, val): The same as new(ref, val), but instead of putting val in a new place in the heap, it replaces the old value at the address stored in ref. 
  rH(ref): Reads the value stored in the heap at the address from ref.
  Nop: Nothing happens.
  
The tables:
  From left to right:
    Heap Table: Contains the elements from the heap. It has two columns: the address and the value.
    Output Table: Contains the output of the program. The newest output is at the top.
    File Table: Contains the open files.
    Program States Table: Contains all active threads and allows you to change the thread you are looking at.
    Symbol Table: Contains all the variables. It has two columns: the name and the value.
    Execution Stack: Shows the code that has yet to run.
