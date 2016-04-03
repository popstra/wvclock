# jQuery mobile #

A unified, HTML5-based user interface system for all popular mobile device platforms, built on the rock-solid jQuery and jQuery UI foundation. Its lightweight code is built with progressive enhancement, and has a flexible, easily themeable design.


## Library name, location and usage ##

jQuery library is installed under the folder assets/www/libs/mobile/1.1.0/.  Generally speaking, I install the minimized versions, jquery.mobile.min.js and jquery.mobile.min.css.  I rename the actual file to the generic name jquery.mobile.min.js and place it in a directory identified by the version number.  It's my preference.  Feel free to change this.

In index.html, I have the line:

```
  <link rel="stylesheet" href="libs/mobile/1.1.0/jquery.mobile.min.css" />
  <script src="libs/mobile/1.1.0/jquery.mobile.min.js"></script>
```


## Summary ##

  * [jquery mobile web site](http://www.jquerymobile.com)
  * Library path: assets/www/libs/mobile/version/
    * jquery.mobile.min.js
    * jquery.mobile.min.css
  * Defined in head section assets/www/index.html
  * Path in index.html is a relative path


## Notes ##

I've found it tidier to keep the libraries in directories identified by version number.  It makes it easy to switch between versions if I find that the library has a problem.  Finally, these libraries are small enough to distribute multiple versions of libraries with distributions.  When I feel comfortable with a particular version, I remove the older ones.

Click the logo below to go to the [jQuery mobile](http://www.jquerymobile.com/) site:

[![](http://jquerymobile.com/demos/1.2.0/docs/_assets/images/jquery-logo.png)](http://www.jquerymobile.com/)


