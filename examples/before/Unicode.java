class Unicode {
        public static void main(String[] args) {
                System.out.println("A = \uuu0041");
        }
        private void printSomething() {
        	int a = 6;
        	double x = 2.1;
        	System.out.println(a+x);
        }
        
        public static final int sum(int a, int b) {
        	return a + b;
        }
        
        private Currency div(Currency a, Currency b, Currency c) {
        	c = a / b;
        	return c;
        }   
}
