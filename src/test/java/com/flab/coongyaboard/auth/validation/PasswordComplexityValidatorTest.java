package com.flab.coongyaboard.auth.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PasswordComplexityValidatorTest {

    private final PasswordComplexityValidator validator = new PasswordComplexityValidator();

    private boolean isValid(String password) {
        return validator.isValid(password, null);
    }

    @Test
    @DisplayName("영문 + 숫자 + 특수문자 있으면 성공")
    void validPassword() {
        assertThat(isValid("password123!")).isTrue();
    }

    @Test
    @DisplayName("영문 없으면 실패")
    void invalidWhenNoLetter() {
        assertThat(isValid("1234567!")).isFalse();
    }

    @Test
    @DisplayName("숫자 없으면 실패")
    void invalidWhenNoDigit() {
        assertThat(isValid("helloworld!@#")).isFalse();
    }

    @Test
    @DisplayName("특수문자 없으면 실패")
    void invalidWhenNoSpecial() {
        assertThat(isValid("Password123")).isFalse();
    }

    @Test
    @DisplayName("영문 대신 한글은 실패")
    void invalidWhenKorean() {
        assertThat(isValid("비밀번호123!")).isFalse();
    }

    @Test
    @DisplayName("특수문자 대신 이모티콘은 실패")
    void invalidWhenImoji() {
        assertThat(isValid("Password123❤")).isFalse();
    }

    @Test
    @DisplayName("null/blank는 성공 처리")
    void nullOrBlank() {
        assertThat(isValid(null)).isTrue();
        assertThat(isValid("")).isTrue();
        assertThat(isValid(" ")).isTrue();
    }
}
