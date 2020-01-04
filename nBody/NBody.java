/* *****************************************************************************
 *  Name:    Wester J. Aldarondo Torres
 *  NetID:   wester.aldarondo@upr.edu
 *  Precept: P00
 *
 *  Description:  Write a program to simulate the motion of n particles in the
 *  plane, mutually affected by gravitational forces, and animate the results.
 *
 **************************************************************************** */

public class NBody {
    public static void main(String[] args) {
        // Step 1. Parse command-line arguments.
        double ax, ay, f, calc;
        double tau = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);

        // Step 2. Read universe from standard input.
        int n = StdIn.readInt();
        double radius = StdIn.readDouble();
        double[] fx = new double[n];
        double[] fy = new double[n];
        double[] px = new double[n];
        double[] py = new double[n];
        double[] vx = new double[n];
        double[] vy = new double[n];
        double[] mass = new double[n];
        String[] image = new String[n];
        for (int i = 0; i < n; i++) {
            px[i] = StdIn.readDouble();
            py[i] = StdIn.readDouble();
            vx[i] = StdIn.readDouble();
            vy[i] = StdIn.readDouble();
            mass[i] = StdIn.readDouble();
            image[i] = StdIn.readString();
        }

        // Step 3. Initialize standard drawing.
        StdDraw.setXscale(-(radius), radius);
        StdDraw.setYscale(-(radius), radius);
        StdDraw.enableDoubleBuffering();

        // Step 4. Play music on standard audio.
        StdAudio.play("2001.wav");
        // Step 5. Simulate the universe.
        for (double t = 0.0; t < tau; t = t + dt) {
            // Step 5A. Calculate net forces.
            for (int i = 0; i < n; i++) {
                fx[i] = 0;
                fy[i] = 0;
                for (int j = 0; j < n; j++) {
                    if (i != j) {
                        calc = Math.sqrt(Math.pow(px[j] - px[i], 2) + Math.pow(py[j] - py[i], 2));
                        f = (6.67e-11 * mass[j] * mass[i]) / Math.pow(calc, 2);
                        fx[i] = fx[i] + (f * ((px[j] - px[i]) / calc));
                        fy[i] = fy[i] + (f * ((py[j] - py[i]) / calc));
                    }
                }
            }
            // Step 5B. Update velocities and positions.
            for (int j = 0; j < n; j++) {
                ax = fx[j] / mass[j];
                ay = fy[j] / mass[j];
                vx[j] = vx[j] + dt * ax;
                vy[j] = vy[j] + dt * ay;
                px[j] = px[j] + vx[j] * dt;
                py[j] = py[j] + vy[j] * dt;
            }
            // Step 5C. Draw universe to standard drawing.
            StdDraw.picture(0, 0, "starfield.jpg");
            for (int j = 0; j < n; j++) {
                StdDraw.picture(px[j], py[j], image[j]);
            }
            StdDraw.show();
            StdDraw.pause(20);
        }
        // Step 6. Print universe to standard output.
        System.out.printf("%d\n", n);
        System.out.printf("%e\n", radius);
        for (int i = 0; i < n; i++) {
            System.out.printf("%.4e ", px[i]);
            System.out.printf("%.4e ", py[i]);
            System.out.printf("%.4e ", vx[i]);
            System.out.printf("%.4e ", vy[i]);
            System.out.printf("%.4e ", mass[i]);
            System.out.println(image[i]);
        }
    }
}
