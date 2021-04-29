job_status = ENV['JOB_STATUS']

github.dismiss_out_of_range_messages({
  error: false,
  warning: true,
  message: true,
  markdown: true
})

Dir.glob("**/build/reports/ktlint/**/ktlint*.xml").each { |report|
  checkstyle_format.base_path = Dir.pwd
  checkstyle_format.report report.to_s
}

Dir.glob("**/build/reports/lint-results*.xml").each { |report|
  android_lint.skip_gradle_task = true
  android_lint.report_file = report.to_s
  android_lint.filtering = false
  android_lint.lint(inline_mode: true)
}

lint_warning_count = status_report[:warnings].count

Dir.glob("**/build/test-results/*/*.xml").each { |report|
  junit.parse report
  junit.show_skipped_tests = true
  junit.report
}

warn 'Write at least one line in the description of PR.' if github.pr_json['author_association'] != 'OWNER' && github.pr_body.length < 1

warn 'Changes have exceeded 500 lines. Divide if possible.' if git.lines_of_code > 500

return unless status_report[:errors].empty?

return markdown ':heavy_exclamation_mark: Pull request check failed.' if job_status != 'success'

comment = "## Status\n:heavy_check_mark: Pull request check passed."
if lint_warning_count == 0
  markdown comment
else
  markdown comment + " (But **#{lint_warning_count}** warnings reported by Android Lint and ktlint.)"
  warn status_report[:warnings]
end
