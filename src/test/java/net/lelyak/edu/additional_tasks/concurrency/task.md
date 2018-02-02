We have some slow API client implementation and we need to download and save large numbers of reports. Please see client sample implementation in `ReportingApiClient.java` and `SlowReportingApiClient.java`.

The task is to build a solution which will use `SlowReportApiClient` to download the reports and save them to the local files.

`Business requirements:`

- 500 reports should be downloaded. Use `report_1`, …, `report_500` names.
- Report downloading should be done in parallel.
- Once a report has been downloaded it should be saved immediately to the local file.
- Base folder for saving the files should be configurable.
- Keep in mind changing requirements in the real world scenario. Design components using best practices. For example, we could have a requirement to change `files local saving` to `uploading files to the cloud infrastructure`.

`Technical requirements:`

- All logic should be covered by tests as much as possible.
- Use maven as project build system.
- Use Java 8.
- Use third-party libraries at your choice.
- Use java.util.concurrent for concurrent code.
- Use best coding practices, write javadocs and produce the best code quality you can.
- It’s ok that requirements don’t cover everything, use your own judgment and explain your approach.
- Use GitHub.
- Use TDD approach.