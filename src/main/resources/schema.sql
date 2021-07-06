
drop alias IF EXISTS FUNC_MESSAGES;
//
CREATE ALIAS FUNC_MESSAGES AS $$
String func(java.sql.Connection conn) throws java.sql.SQLException{
    java.sql.ResultSet rs = conn.createStatement().executeQuery("SELECT listagg(e.MESSAGE) FROM ERROR_LOG e");
    if (!rs.next()) throw new IllegalArgumentException("No rows");
    return rs.getString(1);
}
$$;
//

drop alias IF EXISTS PROC_MESSAGES;
//
CREATE ALIAS PROC_MESSAGES AS $$
int proc(java.sql.Connection conn) throws java.sql.SQLException{
    conn.createStatement().executeUpdate("INSERT INTO ERROR_LOG(ID, MESSAGE) "
         + " SELECT NVL(MIN(e.ID),0)-1, 'FOUND: ' || listagg(e.MESSAGE) FROM ERROR_LOG e");
    return 1;
}
$$;
//
drop alias IF EXISTS FUNC_ADD;
//
CREATE ALIAS FUNC_ADD AS $$
int add(Integer a, Integer b) {
    System.out.println(a + b);
    return a + b;
}
$$;
//