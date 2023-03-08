package com.proyecto404.socialnetwork.console.formatters

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import java.time.LocalDateTime

class RelativeTimeFormatterShould {
    @ParameterizedTest
    @CsvSource(
        "2021-08-18T15:25:00, (just now)",
        "2021-08-18T15:20:08, (5 minutes ago)",
        "2021-08-18T14:53:14, (31 minutes ago)",
        "2021-08-18T10:46:23, (4 hours ago)",
        "2021-08-18T10:25:08, (5 hours ago)",
        "2021-08-18T02:46:23, (12 hours ago)",
        "2021-08-02T02:46:23, (16 days ago)",
        "2021-05-02T19:54:13, (more than a month ago)",
    )
    fun `return how long ago the post was posted`(postDate: String, relativeTime: String) {
        assertThat(relativeTimeFormatter.to(date(postDate))).isEqualTo(relativeTime)
    }

    private fun date(text: String) = LocalDateTime.parse(text)

    private val clock = StoppedClock.at(date("2021-08-18T15:25:08"))
    private val relativeTimeFormatter = RelativeTimeFormatter(clock)
}
