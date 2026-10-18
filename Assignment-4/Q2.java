import java.util.*;

class Song {
    private String title;
    private String artist;
    private String album;
    private int durationSeconds;

    public Song(String title, String artist, String album, int durationSeconds) {
        this.title = title;
        this.artist = artist;
        this.album = album;
        this.durationSeconds = durationSeconds;
    }

    public String getTitle() { return title; }
    public String getArtist() { return artist; }
    public String getAlbum() { return album; }
    public int getDurationSeconds() { return durationSeconds; }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Song)) return false;
        Song other = (Song) obj;
        return (title != null && title.equalsIgnoreCase(other.title)) &&
               (artist != null && artist.equalsIgnoreCase(other.artist));
    }

    @Override
    public int hashCode() {
        return Objects.hash(title == null ? "" : title.toLowerCase(),
                            artist == null ? "" : artist.toLowerCase());
    }

    @Override
    public String toString() {
        return String.format("%s - %s (%s) [%s]", title, artist, album, formatDuration(durationSeconds));
    }

    private static String formatDuration(int seconds) {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format("%d:%02d", minutes, remainingSeconds);
    }
}

public class Q2 {
    private static LinkedList<Song> playlist = new LinkedList<>();
    private static LinkedList<Song> recentHistory = new LinkedList<>();
    private static final int MAX_HISTORY_SIZE = 5;
    private static ListIterator<Song> playbackIterator = null;
    private static Song currentSong = null;
    private static boolean lastDirectionForward = true;

    public static void main(String[] args) {
        System.out.println("=== Media Player Queue Engine Demo ===\n");

        System.out.println("--- Adding Songs to Queue ---");
        addSongToQueue(new Song("Blinding Lights", "The Weeknd", "After Hours", 200));
        addSongToQueue(new Song("Shape of You", "Ed Sheeran", "Divide", 233));
        addSongToQueue(new Song("Levitating", "Dua Lipa", "Future Nostalgia", 203));
        addSongToQueue(new Song("Stay", "The Kid LAROI & Justin Bieber", "F*ck Love", 141));

        printPlaylist();

        System.out.println("\n--- Priority Queueing (Add to Front) ---");
        addSongToFront(new Song("Watermelon Sugar", "Harry Styles", "Fine Line", 174));
        addSongToFront(new Song("Dance Monkey", "Tones and I", "The Kids Are Coming", 208));

        printPlaylist();

        System.out.println("\n--- Adding Duplicate Songs ---");
        addSongToQueue(new Song("Blinding Lights", "The Weeknd", "After Hours", 200));
        addSongToFront(new Song("Watermelon Sugar", "Harry Styles", "Fine Line", 174));

        printPlaylist();

        System.out.println("\n--- Inserting at Position ---");
        insertSongAtPosition(2, new Song("Peaches", "Justin Bieber", "Justice", 198));

        printPlaylist();

        System.out.println("\n--- Removing Duplicates ---");
        forceAddSong(new Song("Shape of You", "Ed Sheeran", "Divide", 233));
        forceAddSong(new Song("Peaches", "Justin Bieber", "Justice", 198));
        printPlaylist();
        removeDuplicates();
        printPlaylist();

        System.out.println("\n--- Bidirectional Navigation (Forward) ---");
        navigateForward();

        System.out.println("\n--- Bidirectional Navigation (Backward) ---");
        navigateBackward();

        System.out.println("\n--- ListIterator next() / previous() Demo ---");
        demonstrateIteratorNavigation();

        System.out.println("\n--- Removing Song ---");
        removeSongByTitle("Stay");
        printPlaylist();

        System.out.println("\n--- Playback Navigation ---");
        currentTrack();
        nextTrack();
        currentTrack();
        nextTrack();
        previousTrack();
        currentTrack();

        System.out.println("\n--- Recent Playback History ---");
        printRecentHistory();

        System.out.println("\n=== Demo Complete ===");
    }

    public static void forceAddSong(Song song) {
        playlist.addLast(song);
        playbackIterator = null;
        System.out.println("FORCE ADDED: " + song.getTitle() + " - " + song.getArtist());
    }

    public static void recordHistory(Song song) {
        if (song == null) return;
        recentHistory.remove(song);
        recentHistory.addFirst(song);
        if (recentHistory.size() > MAX_HISTORY_SIZE) {
            recentHistory.removeLast();
        }
    }

    public static void printRecentHistory() {
        System.out.println("Recent Playback History (" + recentHistory.size() + " songs):");
        if (recentHistory.isEmpty()) {
            System.out.println("  (empty)");
            return;
        }
        int i = 1;
        for (Song song : recentHistory) {
            System.out.println("  " + i + ". " + song);
            i++;
        }
    }

    public static void addSongToQueue(Song song) {
        if (containsSong(song)) {
            System.out.println("SKIPPED (duplicate): " + song.getTitle() + " - " + song.getArtist());
            return;
        }
        playlist.addLast(song);
        playbackIterator = null;
        System.out.println("ADDED (back): " + song.getTitle() + " - " + song.getArtist());
    }

    public static void addSongToFront(Song song) {
        if (containsSong(song)) {
            System.out.println("SKIPPED (duplicate): " + song.getTitle() + " - " + song.getArtist());
            return;
        }
        playlist.addFirst(song);
        playbackIterator = null;
        System.out.println("ADDED (front): " + song.getTitle() + " - " + song.getArtist());
    }

    public static void insertSongAtPosition(int position, Song song) {
        if (position < 0 || position > playlist.size()) {
            System.out.println("INVALID POSITION: " + position);
            return;
        }
        if (containsSong(song)) {
            System.out.println("SKIPPED (duplicate): " + song.getTitle() + " - " + song.getArtist());
            return;
        }
        playlist.add(position, song);
        playbackIterator = null;
        System.out.println("INSERTED at position " + position + ": " + song.getTitle() + " - " + song.getArtist());
    }

    public static boolean containsSong(Song song) {
        return playlist.contains(song);
    }

    public static void removeDuplicates() {
        Set<Song> seen = new LinkedHashSet<>();
        Iterator<Song> it = playlist.iterator();
        int removedCount = 0;

        while (it.hasNext()) {
            Song song = it.next();
            if (!seen.add(song)) {
                it.remove();
                removedCount++;
            }
        }

        playbackIterator = null;
        if (removedCount > 0) {
            System.out.println("Removed " + removedCount + " duplicate song(s).");
        } else {
            System.out.println("No duplicates found.");
        }
    }

    public static void removeSongByTitle(String title) {
        Iterator<Song> it = playlist.iterator();
        boolean found = false;

        while (it.hasNext()) {
            Song song = it.next();
            if (song.getTitle().equalsIgnoreCase(title)) {
                it.remove();
                System.out.println("REMOVED: " + song.getTitle() + " - " + song.getArtist());
                found = true;
            }
        }

        if (found) {
            playbackIterator = null;
        } else {
            System.out.println("Song not found: " + title);
        }
    }

    public static void navigateForward() {
        System.out.println("Playing playlist forward:");
        ListIterator<Song> it = playlist.listIterator();
        while (it.hasNext()) {
            Song song = it.next();
            System.out.println("  [" + it.nextIndex() + "] " + song.getTitle() + " - " + song.getArtist());
        }
    }

    public static void navigateBackward() {
        System.out.println("Playing playlist backward:");
        ListIterator<Song> it = playlist.listIterator(playlist.size());
        while (it.hasPrevious()) {
            Song song = it.previous();
            System.out.println("  [" + it.previousIndex() + "] " + song.getTitle() + " - " + song.getArtist());
        }
    }

    public static void demonstrateIteratorNavigation() {
        ListIterator<Song> it = playlist.listIterator();

        System.out.println("Moving forward...");
        if (it.hasNext()) System.out.println("  Now: " + it.next());
        if (it.hasNext()) System.out.println("  Next: " + it.next());

        System.out.println("Moving backward...");
        if (it.hasPrevious()) System.out.println("  Previous: " + it.previous());
        if (it.hasPrevious()) System.out.println("  Previous: " + it.previous());

        System.out.println("Moving forward again...");
        if (it.hasNext()) System.out.println("  Next: " + it.next());
        if (it.hasNext()) System.out.println("  Next: " + it.next());
    }

    public static void currentTrack() {
        if (currentSong != null) {
            System.out.println("Current track: " + currentSong);
        } else {
            System.out.println("No track currently selected.");
        }
    }

    public static void nextTrack() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }
        if (playbackIterator == null) {
            playbackIterator = playlist.listIterator();
            lastDirectionForward = true;
        }
        if (!lastDirectionForward && playbackIterator.hasNext()) {
            playbackIterator.next();
        }
        if (playbackIterator.hasNext()) {
            currentSong = playbackIterator.next();
            lastDirectionForward = true;
            recordHistory(currentSong);
        } else {
            playbackIterator = playlist.listIterator();
            if (playbackIterator.hasNext()) {
                currentSong = playbackIterator.next();
                lastDirectionForward = true;
                recordHistory(currentSong);
            }
        }
        currentTrack();
    }

    public static void previousTrack() {
        if (playlist.isEmpty()) {
            System.out.println("Playlist is empty.");
            return;
        }
        if (playbackIterator == null) {
            playbackIterator = playlist.listIterator(playlist.size());
            lastDirectionForward = false;
        }
        if (lastDirectionForward && playbackIterator.hasPrevious()) {
            playbackIterator.previous();
        }
        if (playbackIterator.hasPrevious()) {
            currentSong = playbackIterator.previous();
            lastDirectionForward = false;
            recordHistory(currentSong);
        } else {
            playbackIterator = playlist.listIterator(playlist.size());
            if (playbackIterator.hasPrevious()) {
                currentSong = playbackIterator.previous();
                lastDirectionForward = false;
                recordHistory(currentSong);
            }
        }
        currentTrack();
    }

    public static void printPlaylist() {
        System.out.println("Playlist (" + playlist.size() + " songs):");
        if (playlist.isEmpty()) {
            System.out.println("  (empty)");
            return;
        }
        int i = 0;
        for (Song song : playlist) {
            System.out.println("  [" + i + "] " + song);
            i++;
        }
    }
}