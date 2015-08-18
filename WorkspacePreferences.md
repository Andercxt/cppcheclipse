# Introduction #
You can configure the following topics here
  1. [General](#General.md)
  1. [Problems](#Problems.md)
  1. [Settings](#Settings.md)

# General #
![http://cppcheclipse.googlecode.com/svn/images/preferences-general.gif](http://cppcheclipse.googlecode.com/svn/images/preferences-general.gif)

**cppcheck binary path** Here you must enter the path to a valid cppcheck binary. It is automatically checked if the given binary fulfills the version requirement.

**Automatic update check** If checked, cppcheclipse checks in the given intervals for new versions of cppcheck. The date of the last update check is given below. Notice that this update checks only checks for updates of cppcheck but not for cppcheclipse itself. For automatic update checking for Eclipse plugins click the link below.

**Check for update now...** Checks for an update of cppcheck. If available, it provides a link to the download page of cppcheck.

# Problems #
![http://cppcheclipse.googlecode.com/svn/images/preferences-problems.gif](http://cppcheclipse.googlecode.com/svn/images/preferences-problems.gif)

With this table you can suppress the output for some or all of the issues. When an issue is selected it is reported in the Eclipse problems view with the given severity, otherwise not. You can select either whole groups (possible error, error, style, possible style) or individual problems. This list contains all issues, the given cppcheck binary checks for. Please note that some of the issues are only given out by cppcheck only, if the appropriate flags are set.

# Settings #
![http://cppcheclipse.googlecode.com/svn/images/preferences-settings.gif](http://cppcheclipse.googlecode.com/svn/images/preferences-settings.gif)

Have a look at the [cppcheck manual](http://cppcheck.sourceforge.net/manual.pdf) for a more detailed description of the command line arguments.

**Number of thread to use** Gives the number of threads, cppcheck uses in parallel. May lead to better performance in multicore processors. This is automatically disabled if the check for unused functions is enabled.

**Check for all known issues** If checked the other checkboxes are disabled. Leads to checking for all issues, that can be checked by cppcheck.

**Check for coding style issues** Checks for issues which are regarded as bad coding style.

**Check for possible errors** Checks for issues, which may be errors, but in some cases, those issues may be false positives. If "Check for coding style issues" is also set, there are also potential style issues reported.

**Check for unused functions** Checks for functions which are not used throughout the code, and can therefore be safely deleted.

**Check for exception safety of new** Checks whether new calls are handled in an exception-safe way.

**Check for exception safety of realloc** Checks whether realloc calls are handled in an exception-safe way.

**Force all configurations** If you have a file with a lot of #ifdefs, you must set this checkbox to force cppcheck to try out all possible configurations. This may lead to bad check performance.

**Verbose** Makes the output of cppcheck more verbose.

**Use inline suppressions** You can suppress issues with special formatted comments within the code. As an alternative you can use the cppcheclipse suppression table.

**Debug** Only use this for debugging purposes. You can see an extended output of cppcheck in the console, which tells you whether the tokenizer works correctly.