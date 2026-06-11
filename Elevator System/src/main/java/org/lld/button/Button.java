package org.lld.button;

import org.lld.request.Request;

/**
 * A button produces a request rather than doing the work itself — essentially
 * the command pattern: the button is decoupled from whoever handles the request.
 */
public interface Button {
    Request press();
}
