/*
 * Copyright (C) 2011 Everit Kft. (http://www.everit.org)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.everit.json.schema.internal;

import java.net.InetAddress;
import java.util.Optional;

import com.google.common.net.InetAddresses;

/**
 * Common superclass for {@link IPV4Validator} and {@link IPV6Validator}.
 */
public class IPAddressValidator {

  /**
   * Creates an {@link InetAddress} instance if possible and returns it, or on failure it returns
   * {@code Optional.empty()}.
   *
   * @param subject
   *          the string to be validated.
   * @return the optional validation failure message
   */
  protected Optional<InetAddress> asInetAddress(final String subject) {
    try {
      if (InetAddresses.isInetAddress(subject)) {
        return Optional.of(InetAddresses.forString(subject));
      } else {
        return Optional.empty();
      }
    } catch (NullPointerException e) {
      return Optional.empty();
    }
  }

  /**
   * Checks an IP address.
   *
   * @param subject
   *          the string to be validated.
   * @param expectedLength
   *          the expected length of {@code subject} - it is validated if
   *          {@link #asInetAddress(String)} validation succeeds.
   * @param failureFormat
   *          the {@link String#format(String, Object...) string format} of the validation failue
   *          message. The format string will receive only the {@code subject} parameter (so it has
   *          to be referred as {@code %s} in the format string
   * @return the optional validation failure message
   */
  protected Optional<String> checkIpAddress(final String subject, final int expectedLength,
      final String failureFormat) {
    return asInetAddress(subject)
        .filter(addr -> addr.getAddress().length == expectedLength)
        .map(addr -> emptyString())
        .orElse(Optional.of(String.format(failureFormat, subject)));
  }

  private Optional<String> emptyString() {
    return Optional.empty();
  }

}
