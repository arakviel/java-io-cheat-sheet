# Java IO | NIO | NIO2 Cheat Sheet

_Use project for study_

## Requires

* java version 11+

## Some tips

* It is relevant to use the <code>java.nio</code> package and methods from the <code>Files</code>
  utility file.
* In modern realities, Java NIO (Buffer oriented) takes about 80-90% of the work with files,
  although the share of Java IO is still significant.
* Always for path to files, used <code>Path</code> interface and <code>of()</code> static method.
  It's generating OS independent path and has some usefully methods.