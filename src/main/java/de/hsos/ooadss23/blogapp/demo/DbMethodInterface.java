package de.hsos.ooadss23.blogapp.demo;

import java.sql.SQLException;

@FunctionalInterface
public interface DbMethodInterface {
    Object apply() throws SQLException;
}
