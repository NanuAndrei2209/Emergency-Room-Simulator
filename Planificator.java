import java.util.Iterator;
    class Proces {
        Proces(int p) {
            this.prioritate = p;
        }

        public int prioritate;
    }
    public class Planificator {
        private Planificator() {
            utilA = new Proces[3];
            utilA[0] = new Proces(3);
            utilA[1] = new Proces(2);
            utilA[2] = new Proces(1);
            utilB = new Proces[3];
            utilB[0] = new Proces(2);
            utilB[1] = new Proces(2);
            utilB[2] = new Proces(0);

        }
        private Proces[] utilA; // utilA[0..dimA-1] contine procesele utilizatorului A in ordinea prioritatii
        private Proces[] utilB; // utilB[0..dimB-1] contine procesele utilizatorului B in  ordinea prioritatii

        // /** un iterator al Proceselor in ordinea prioritatii */
        public Iterator iterator() {
            return new ProcesIterator();
        }
        public static void main(String[] args) {
            Planificator plan = new Planificator();
            Iterator<Proces> p = plan.iterator();
            while(p.hasNext()) {
                System.out.println(p.next().prioritate);
            }
        }
        class ProcesIterator implements Iterator<Proces> {
            public int i;
            public int j;
            ProcesIterator() {
                i = 0;
                j = 0;
            }
            @Override
            public boolean hasNext() {
                if (i == utilA.length && j == utilB.length) {
                    return false;
                }
                return true;
            }

            @Override
            public Proces next() {
                if (j == utilB.length) {
                    i++;
                    return utilA[i-1];
                }
                if (i == utilA.length) {
                    j++;
                    return utilB[j - 1];
                }
                if (utilA[i].prioritate < utilB[j].prioritate) {
                    j++;
                    return utilB[j-1];
                }
                i++;
                return utilA[i-1];

            }
        }
}