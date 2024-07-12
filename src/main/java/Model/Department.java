package Model;

public class Department {

        private int id;
        private String name;

        public Department(int id, String name) {
            this.id = id;
            this.name = name;
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
