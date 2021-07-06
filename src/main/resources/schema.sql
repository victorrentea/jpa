drop alias IF EXISTS FUN_MESSAGES_CSV;
//
CREATE ALIAS FUN_MESSAGES_CSV AS $$
String aggregateLogs(java.sql.Connection conn) throws java.sql.SQLException{
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
      java.sql.ResultSet rs = conn.createStatement().executeQuery("SELECT listagg(e.MESSAGE) FROM ERROR_LOG e");
       if (!rs.next()) throw new IllegalArgumentException("No rows");
       String csv = rs.getString(1);
       conn.createStatement().executeUpdate("INSERT INTO ERROR_LOG(ID, MESSAGE) SELECT MIN(e.ID)-1, 'FOUND: "+ csv +"' FROM ERROR_LOG e");
       return 1;
}
$$;
//