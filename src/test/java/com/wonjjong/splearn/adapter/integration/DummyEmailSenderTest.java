package com.wonjjong.splearn.adapter.integration;

import com.wonjjong.splearn.domain.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junitpioneer.jupiter.StdIo;
import org.junitpioneer.jupiter.StdOut;

import static org.assertj.core.api.Assertions.assertThat;

class DummyEmailSenderTest {
    @Test
    @DisplayName("dummyEmailSender")
    @StdIo
    void dummyEmailSender(StdOut stdOut) {
        DummyEmailSender dummyEmailSender = new DummyEmailSender();

        dummyEmailSender.send(new Email("mock@gmail.com"), "Test Subject", "Test Body");

        assertThat(stdOut.capturedLines()[0]).isEqualTo("DummyEmailSender send email : Email[address=mock@gmail.com]");
    }


}