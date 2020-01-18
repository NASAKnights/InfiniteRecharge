# Infinite Recharge: GitHub Code of Conduct

Welcome to the Infinite Recharge repository, a place for team and community members alike to offer suggestions, contributions, and to observe our progress!
If you're new to our projects or have never read this document, we strongly advise you do so to get the most out of your time here. This document outlines different guidelines relating to documentation, pull requests, issues, and more.
Please ensure to comply with these guidelines to expedite reviews of issues, pull requests, and other contributions. Gross violations, repeated violations, or abuse may result in being barred from this repository or any other repositories controlled by FRC Team 122.

## Code Style Guidelines

By committing changes to this repository or any of its branches, you are agreeing to abide by the following:

- All brackets in Java code should be wrapped to the next line
- Interface file names should begin with an 'I', as such: ISerializable
- Constants should be upper snake case, such as: `FRONT_LEFT_DRIVE_ID`, `DRIVER_ID`, or `VISION_IP`
- Camel case should be used for method and variable names, as such: getDriveMotorID()
- Excess spaces and / or whitespace should not be present
- Methods directly interacting with subsystems should be placed in their respective subsystem's class
- Methods for use inside a command should generally not be public; if it has uses across multiple commands in a subsystem, it should be placed in the subsystem's class or if it has uses independent of the subsystem, it should be placed in the `xyz.nasaknights.infiniterecharge.util` package
- Comments should be used to describe all methods, and are required to be used when there is any object that may contain ambiguity as to its purpose and / or usage
- Obscenities are never allowed in any comments, method names, variable names, class / enumerator / interface names, or anywhere else within the codebase

## Submitting a proper issue or pull request

Issues and pull requests are the best way to ensure that the quality of code is checked and documentated, as well as to protect from abuse.

When submitting an issue, it is requested that you:

- Provide a short, yet descriptive title of your issue
- Attach as much information relating to your issue (if a bug report, attach stacktraces and any appropriate debug files; if an enhancement or feature request, please attach an in-depth description)
- Keep up to date with the issue: respond to requests for information and monitor responses
- Attach references to commit(s) that may have caused the issue (if applicable)
- Attach references to pull request(s) that may contain a fix

When submitting a pull request, it is requested that you:

- Provide a short, yet descriptive title regarding the content of your issue
- Attach as much information regarding changes you made, and the justification behind them
- Attach references to issues (if necessary)
- Ensure final version of branch to be merged is compliant with the Code Style Guidelines, found earlier in this document

## Interactions within Issues, Pull Requests, Comments, etc.

When making any comments or interactions with other members on this repository and others under the NASAKnights organization, you are exepcted to:

- Be respectful of others and their ideas
- Offer suggestions and feedback, if possible
- Be constructive; do not simply make comments for no reason or observations that are reasonably implied
- Do not attach external links from a dubious source

## Conclusion

Please ensure to follow these guidelines to prevent any setbacks in the processing of your pull request or issue, and to ensure that everyone is able to contribute to the project in an open and constructive manner.

Last Revised: Sunday, January 12, 2020 10:26:37 PM GMT-5:00
