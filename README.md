# HackScript

HackScript is a C-like language that compiles to [HVM](http://www.hacker.org/hvm/).

## Example

The following code:

```c
// Try with memory cells 72,101,108,108,111,32,87,111,114,108,100,33
void main() {
    int i = 0;

    while (*i != 0) {
        pprint(*i);
        i++;
    }
}
```

will produce:

```python
0999**>999**<<0:3?11g00:95*6-?999**<<P0d999**<1+999**>999**<d097*1--g
```

## TODOs:

* Functions
    * Parameters
    * Recursion
* Arrays
* Break
* Continue

