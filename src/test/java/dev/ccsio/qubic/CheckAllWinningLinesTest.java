package dev.ccsio.qubic;

import dev.ccsio.qubic.game.WinningLinesRecord;
import dev.ccsio.qubic.types.Coordinates;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class CheckAllWinningLinesTest {

    @Test
    public void testCheckAllWinningLines() {
        // Generate all possible winning lines
        Set<List<Coordinates>> generatedLines = generateAllWinningLines();

        // Get winning lines from WinningLinesRecord
        WinningLinesRecord winningLinesRecord = new WinningLinesRecord();
        List<List<Coordinates>> recordLines = winningLinesRecord.getWinningLines();

        // Convert record lines to normalized set for comparison
        Set<List<Coordinates>> normalizedRecordLines = new HashSet<>();
        for (List<Coordinates> line : recordLines) {
            List<Coordinates> reversed = new ArrayList<>(line);
            Collections.reverse(reversed);
            List<Coordinates> canonical = compareLineLex(line, reversed) <= 0 ? line : reversed;
            normalizedRecordLines.add(canonical);
        }

        // Print results
        System.out.println("Generated lines count: " + generatedLines.size());
        System.out.println("WinningLinesRecord count: " + normalizedRecordLines.size());

        // Check if they match
        assertEquals(generatedLines.size(), normalizedRecordLines.size(),
                "Number of winning lines should match");

        // Find lines in generated but not in record
        Set<List<Coordinates>> missingInRecord = new HashSet<>(generatedLines);
        missingInRecord.removeAll(normalizedRecordLines);

        // Find lines in record but not in generated
        Set<List<Coordinates>> extraInRecord = new HashSet<>(normalizedRecordLines);
        extraInRecord.removeAll(generatedLines);

        if (!missingInRecord.isEmpty()) {
            System.out.println("\nLines missing from WinningLinesRecord:");
            for (List<Coordinates> line : missingInRecord) {
                System.out.println("  " + line);
            }
        }

        if (!extraInRecord.isEmpty()) {
            System.out.println("\nExtra lines in WinningLinesRecord:");
            for (List<Coordinates> line : extraInRecord) {
                System.out.println("  " + line);
            }
        }

        assertTrue(missingInRecord.isEmpty(),
                "WinningLinesRecord is missing " + missingInRecord.size() + " lines");
        assertTrue(extraInRecord.isEmpty(),
                "WinningLinesRecord has " + extraInRecord.size() + " extra lines");

        System.out.println("\n✓ All winning lines match!");
    }

    private Set<List<Coordinates>> generateAllWinningLines() {
        Set<List<Coordinates>> lines = new HashSet<>();

        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                for (int z = 0; z < 4; z++) {
                    for (int dx = -1; dx <= 1; dx++) {
                        for (int dy = -1; dy <= 1; dy++) {
                            for (int dz = -1; dz <= 1; dz++) {
                                if (dx == 0 && dy == 0 && dz == 0) continue;

                                List<Coordinates> line = new ArrayList<>();
                                for (int step = 0; step < 4; step++) {
                                    int nx = x + dx * step;
                                    int ny = y + dy * step;
                                    int nz = z + dz * step;

                                    if (nx < 0 || nx >= 4 || ny < 0 || ny >= 4 || nz < 0 || nz >= 4) {
                                        line = null;
                                        break;
                                    }

                                    line.add(new Coordinates(nx, ny, nz));
                                }

                                if (line != null && line.size() == 4) {
                                    // Normalize by ensuring direction (dx,dy,dz) points toward positive or lexicographically smallest start
                                    List<Coordinates> reversed = new ArrayList<>(line);
                                    Collections.reverse(reversed);
                                    List<Coordinates> canonical = compareLineLex(line, reversed) <= 0 ? line : reversed;
                                    lines.add(canonical);
                                }
                            }
                        }
                    }
                }
            }
        }

        return lines;
    }

    // Utility to compare two lists of coordinates lexicographically
    private int compareLineLex(List<Coordinates> a, List<Coordinates> b) {
        for (int i = 0; i < a.size(); i++) {
            int cmp = a.get(i).toString().compareTo(b.get(i).toString());
            if (cmp != 0) return cmp;
        }
        return 0;
    }
}