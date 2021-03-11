package com.example.payroll;

class HeroNotFoundException extends RuntimeException {

  HeroNotFoundException(Long id) {
    super("Could not find employee " + id);
  }
}