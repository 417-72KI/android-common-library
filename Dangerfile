job_status = ENV['JOB_STATUS']

ignore_lint_project_list = ['app-sample']

github.dismiss_out_of_range_messages({
  error: false,
  warning: true,
  message: true,
  markdown: true
})

Dir.glob("**/build/reports/**/build-error-log.txt")
  .filter { |error_log| ignore_lint_project_list.all? { |v| !error_log.start_with?(v) } }
  .each { |error_log|
    File.open(error_log.to_s, "r") do |error_log|
      error_log.each_line { |line|
        warn line[3..-1] if line.start_with?("w: ")
        fail line[3..-1] if line.start_with?("e: ")
      }
    end
  }

Dir.glob("**/build/reports/ktlint/**/ktlint*.xml")
  .filter { |report| ignore_lint_project_list.all? { |v| !report.start_with?(v) } }
  .each { |report|
    checkstyle_format.base_path = Dir.pwd
    checkstyle_format.report report.to_s
  }

Dir.glob("**/build/reports/lint-results*.xml")
  .filter { |report| ignore_lint_project_list.all? { |v| !report.start_with?(v) } }
  .each { |report|
    android_lint.skip_gradle_task = true
    android_lint.report_file = report.to_s
    android_lint.filtering = false
    android_lint.lint(inline_mode: true)
  }

Dir.glob("**/build/test-results/*/*.xml")
  .filter { |report| ignore_lint_project_list.all? { |v| !report.start_with?(v) } }
  .each { |report|
    junit.parse report
    junit.show_skipped_tests = true
    junit.report
  }

warn 'Write at least one line in the description of PR.' if github.pr_json['author_association'] != 'OWNER' && github.pr_body.length < 1

warn 'Changes have exceeded 500 lines. Divide if possible.' if git.lines_of_code > 500

warn status_report[:warnings]
fail status_report[:errors]

return unless status_report[:errors].empty?

return fail ':heavy_exclamation_mark: Pull request check failed.' if job_status != 'success'

markdown "## Status\n:tada: Pull request check passed."
markdown ":warning: Some warnings reported by Android Lint or ktlint." unless status_report[:warnings].empty?
