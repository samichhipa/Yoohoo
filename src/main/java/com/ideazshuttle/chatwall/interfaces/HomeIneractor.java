package com.ideazshuttle.chatwall.interfaces;

import com.ideazshuttle.chatwall.models.Contact;

import java.util.HashMap;

/**
 * Created by a_man on 01-01-2018.
 */

public interface HomeIneractor {
    HashMap<String, Contact> getLocalContacts();
}
