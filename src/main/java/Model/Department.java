package Model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Department {

        private int id;
        private String name;

        public Department(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Department(ResultSet resultSet) throws SQLException {
            this.id = resultSet.getInt("department_id");
            this.name = resultSet.getString("department_name");
        }

        public String toString() {
            return String.format("Department id: %d, name: %s", id, name);
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }
}
