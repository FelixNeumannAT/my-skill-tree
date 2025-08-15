package io.github.felixneumannat.myskilltree.core.geometry;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.truth.Truth.*;

public class Vec2iTest {

    private static final double DOUBLE_FAULT_TOLERANCE = 1e-6;

    @Test
    public void defaultConstructor_buildsZeroVector() {
        Vec2i vec = new Vec2i();
        Vec2i expected = new Vec2i(0, 0);

        assertThat(vec.getX()).isEqualTo(0);
        assertThat(vec.getY()).isEqualTo(0);
        assertThat(vec).isEqualTo(expected);
        assertThat(vec).isEqualTo(Vec2i.ZERO);
    }

    @Test
    public void constructor_setsFields() {
        Vec2i vec = new Vec2i(7, -3);
        assertThat(vec.getX()).isEqualTo(7);
        assertThat(vec.getY()).isEqualTo(-3);
    }

    @Test
    public void addVec_returnsCorrectVec_andDoesNotMutate_andIsCommutative() {
        Vec2i vec1 = new Vec2i(1, 2);
        Vec2i vec2 = new Vec2i(3, -4);
        Vec2i expectedVec1 = new Vec2i(1, 2);
        Vec2i expectedVec2 = new Vec2i(3, -4);
        Vec2i expectedResult = new Vec2i(4, -2);

        Vec2i result1 = vec1.add(vec2);
        Vec2i result2 = vec2.add(vec1);
        Vec2i result3 = vec1.add(Vec2i.ZERO);

        assertThat(vec1).isEqualTo(expectedVec1);
        assertThat(vec2).isEqualTo(expectedVec2);
        assertThat(result1).isEqualTo(expectedResult);
        assertThat(result2).isEqualTo(expectedResult);
        assertThat(result3).isNotSameInstanceAs(vec1);
    }

    @Test
    public void addScalars_returnsCorrectVec_andDoesNotMutate() {
        Vec2i vec = new Vec2i(1, 2);
        Vec2i expectedVec = new Vec2i(1, 2);
        Vec2i expectedResult = new Vec2i(4, -2);

        Vec2i result1 = vec.add(3, -4);
        Vec2i result2 = vec.add(0, 0);

        assertThat(vec).isEqualTo(expectedVec);
        assertThat(result1).isEqualTo(expectedResult);
        assertThat(result2).isNotSameInstanceAs(vec);
    }

    @Test
    public void add_overflowWraps_andUnderflowWraps() {
        Vec2i vec1 = new Vec2i(Integer.MAX_VALUE, 0);
        Vec2i vec2 = new Vec2i(Integer.MIN_VALUE, 0);

        Vec2i result1 = vec1.add(1, 0);
        Vec2i result2 = vec2.add(-1, 0);

        assertThat(result1).isEqualTo(vec2);
        assertThat(result2).isEqualTo(vec1);
    }

    @Test(expected = NullPointerException.class)
    public void add_null_throwsNPE() {
        // noinspection ResultOfMethodCallIgnored,DataFlowIssue
        new Vec2i(1, 2).add(null);
    }

    @Test
    public void subtractVec_returnsCorrectVec_andDoesNotMutate() {
        Vec2i vec1 = new Vec2i(1, 2);
        Vec2i vec2 = new Vec2i(3, -4);
        Vec2i expectedVec1 = new Vec2i(1, 2);
        Vec2i expectedVec2 = new Vec2i(3, -4);
        Vec2i expectedResult = new Vec2i(-2, 6);

        Vec2i result1 = vec1.subtract(vec2);
        Vec2i result2 = vec1.subtract(Vec2i.ZERO);

        assertThat(vec1).isEqualTo(expectedVec1);
        assertThat(vec2).isEqualTo(expectedVec2);
        assertThat(result1).isEqualTo(expectedResult);
        assertThat(result2).isNotSameInstanceAs(vec1);
    }

    @Test
    public void subtractScalars_returnsCorrectVec_andDoesNotMutate() {
        Vec2i vec = new Vec2i(1, 2);
        Vec2i expectedVec = new Vec2i(1, 2);
        Vec2i expectedResult = new Vec2i(-2, 6);

        Vec2i result1 = vec.subtract(3, -4);
        Vec2i result2 = vec.subtract(0, 0);

        assertThat(vec).isEqualTo(expectedVec);
        assertThat(result1).isEqualTo(expectedResult);
        assertThat(result2).isNotSameInstanceAs(vec);
    }

    @Test
    public void subtract_overflowWraps_andUnderflowWraps() {
        Vec2i vec1 = new Vec2i(Integer.MIN_VALUE, 0);
        Vec2i vec2 = new Vec2i(Integer.MAX_VALUE, 0);

        Vec2i result1 = vec1.subtract(1, 0);
        Vec2i result2 = vec2.subtract(-1, 0);

        assertThat(result1).isEqualTo(vec2);
        assertThat(result2).isEqualTo(vec1);
    }

    @Test(expected = NullPointerException.class)
    public void subtract_null_throwsNPE() {
        // noinspection ResultOfMethodCallIgnored,DataFlowIssue
        new Vec2i(1, 2).subtract(null);
    }

    @Test
    public void scale_returnsCorrectVec_andDoesNotMutate() {
        Vec2i vec = new Vec2i(7, -2);
        Vec2i expectedVec = new Vec2i(7, -2);
        Vec2i expectedResult = new Vec2i(21, -6);

        Vec2i result1 = vec.scale(3);
        Vec2i result2 = vec.scale(0);

        assertThat(vec).isEqualTo(expectedVec);
        assertThat(result1).isEqualTo(expectedResult);
        assertThat(result2).isNotSameInstanceAs(vec);
    }

    @Test
    public void scale_overflowWraps_andUnderflowWraps() {
        Vec2i vec1 = new Vec2i(Integer.MIN_VALUE, 7);
        Vec2i vec2 = new Vec2i(Integer.MAX_VALUE, 1);
        Vec2i expected1 = new Vec2i(Integer.MIN_VALUE, -7);
        Vec2i expected2 = new Vec2i(-2, 2);

        Vec2i result1 = vec1.scale(-1);
        Vec2i result2 = vec2.scale(2);

        assertThat(result1).isEqualTo(expected1);
        assertThat(result2).isEqualTo(expected2);
    }

    @Test public void basicIdentityLawsHold() {
        Vec2i vec1 = new Vec2i(123, -456);
        Vec2i vec2 = new Vec2i(-7, 9);
        int s = 13;

        Vec2i expected1 = new Vec2i(123, -456);
        Vec2i expected2 = vec1.scale(s).add(vec2.scale(s));

        Vec2i result1 = vec1.add(0, 0);
        Vec2i result2 = vec1.add(vec2).subtract(vec2);
        Vec2i result3 = vec1.scale(0);
        Vec2i result4 = vec1.add(vec2).scale(s);

        assertThat(result1).isEqualTo(expected1);
        assertThat(result2).isEqualTo(expected1);
        assertThat(result3).isEqualTo(Vec2i.ZERO);
        assertThat(result4).isEqualTo(expected2);
    }

    @Test
    public void dot_returnsCorrectDotProd_andIsCommutative() {
        Vec2i vec1 = new Vec2i(1, 2);
        Vec2i vec2 = new Vec2i(3, -4);
        long expectedDotProd = -5;

        long dotProd1 = vec1.dot(vec2);
        long dotProd2 = vec2.dot(vec1);

        assertThat(dotProd1).isEqualTo(expectedDotProd);
        assertThat(dotProd2).isEqualTo(expectedDotProd);
    }

    @Test
    public void dot_doesNotOverflow_withMaxValues() {
        Vec2i vec = new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
        long maxIntSquared = Math.multiplyExact(Integer.MAX_VALUE, (long) Integer.MAX_VALUE);
        long expectedDotProd = Math.multiplyExact(2L, maxIntSquared);

        long dotProd = vec.dot(vec);

        assertThat(dotProd).isEqualTo(expectedDotProd);
    }

    @Test
    public void dot_overflowWraps_withMinValues() {
        Vec2i vec = new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE);
        long dotProd = vec.dot(vec);
        assertThat(dotProd).isLessThan(0);
    }

    @Test(expected = NullPointerException.class)
    public void dot_null_throwsNPE() {
        // noinspection ResultOfMethodCallIgnored,DataFlowIssue
        new Vec2i(1, 2).dot(null);
    }

    @Test
    public void lengthSquared_returnsZero_forZeroVec() {
        long expectedLengthSq = 0L;
        long lengthSq = Vec2i.ZERO.lengthSquared();
        assertThat(lengthSq).isEqualTo(expectedLengthSq);
    }

    @Test
    public void lengthSquared_returnsCorrectSquaredLength() {
        Vec2i vec = new Vec2i(4, -3);
        long expectedLengthSq = 25L;
        long lengthSq = vec.lengthSquared();
        assertThat(lengthSq).isEqualTo(expectedLengthSq);
    }

    @Test
    public void lengthSquared_doesNotOverflow_withMaxValues() {
        Vec2i vec = new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
        long maxIntSquared = Math.multiplyExact(Integer.MAX_VALUE, (long) Integer.MAX_VALUE);
        long expectedLengthSq = Math.multiplyExact(2L, maxIntSquared);

        long lengthSq = vec.lengthSquared();

        assertThat(lengthSq).isEqualTo(expectedLengthSq);
    }

    @Test
    public void lengthSquared_overflowWraps_withMinValues() {
        Vec2i vec = new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE);
        long lengthSq = vec.lengthSquared();
        assertThat(lengthSq).isLessThan(0);
    }

    @Test
    public void length_returnsZero_forZeroVec() {
        double expectedLength = 0.;
        double length = Vec2i.ZERO.length();
        assertThat(length).isEqualTo(expectedLength);
    }

    @Test
    public void length_returnsCorrectLength() {
        Vec2i vec = new Vec2i(4, -3);
        double expectedLength = 5.;
        double length = vec.length();
        assertThat(length).isWithin(DOUBLE_FAULT_TOLERANCE).of(expectedLength);
    }

    @Test
    public void length_doesNotOverflow_withMaxValues() {
        Vec2i vec = new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE);
        long maxIntSquared = Math.multiplyExact(Integer.MAX_VALUE, (long) Integer.MAX_VALUE);
        long lengthSq = Math.multiplyExact(2L, maxIntSquared);
        double expectedLength = Math.sqrt(lengthSq);

        double length = vec.length();

        assertThat(length).isWithin(DOUBLE_FAULT_TOLERANCE).of(expectedLength);
    }

    @Test
    public void length_returnsNaN_withMinValues() {
        Vec2i vec = new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE);
        double length = vec.length();
        assertThat(Double.isNaN(length)).isTrue();
    }

    @Test
    public void distanceSquared_returnsZero_forSameVec() {
        Vec2i vec = new Vec2i(-1, 2);
        long expectedDistanceSq = 0L;
        long distanceSq = vec.distanceSquared(vec);
        assertThat(distanceSq).isEqualTo(expectedDistanceSq);
    }

    @Test
    public void distanceSquared_returnsCorrectSquaredDistance_andIsSymmetric() {
        Vec2i vec1 = new Vec2i(-3, 2);
        Vec2i vec2 = new Vec2i(1, -1);
        Vec2i vec3 = new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE);

        long expectedDistanceSq1 = 25L;
        long maxIntSquared = Math.multiplyExact(Integer.MAX_VALUE, (long) Integer.MAX_VALUE);
        long expectedDistanceSq2 = Math.multiplyExact(2L, maxIntSquared);

        long distanceSq1 = vec1.distanceSquared(vec2);
        long distanceSq2 = vec2.distanceSquared(vec1);
        long distanceSq3 = Vec2i.ZERO.distanceSquared(vec3);

        assertThat(distanceSq1).isEqualTo(expectedDistanceSq1);
        assertThat(distanceSq2).isEqualTo(expectedDistanceSq1);
        assertThat(distanceSq3).isEqualTo(expectedDistanceSq2);
    }

    @Test
    public void distanceSquared_overflowWraps_withMinMaxValues() {
        Vec2i vec1 = new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE);
        Vec2i vec2 = new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE);

        long distanceSq1 = vec1.distanceSquared(vec2);
        long distanceSq2 = Vec2i.ZERO.distanceSquared(vec1);

        assertThat(distanceSq1).isLessThan(0);
        assertThat(distanceSq2).isLessThan(0);
    }

    @Test(expected = NullPointerException.class)
    public void distanceSquared_null_throwsNPE() {
        // noinspection ResultOfMethodCallIgnored,DataFlowIssue
        new Vec2i(1, 2).distanceSquared(null);
    }

    @Test
    public void distance_returnsZero_forSameVec() {
        Vec2i vec = new Vec2i(-1, 2);
        double expectedDistance = 0.;
        double distance = vec.distance(vec);
        assertThat(distance).isEqualTo(expectedDistance);
    }

    @Test
    public void distance_returnsCorrectDistance_andIsSymmetric() {
        Vec2i vec1 = new Vec2i(-3, 2);
        Vec2i vec2 = new Vec2i(1, -1);
        Vec2i vec4 = new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE);

        double expectedDistance1 = 5.;
        long maxIntSquared = Math.multiplyExact(Integer.MAX_VALUE, (long) Integer.MAX_VALUE);
        long lengthSq = Math.multiplyExact(2L, maxIntSquared);
        double expectedDistance2 = Math.sqrt(lengthSq);

        double distance1 = vec1.distance(vec2);
        double distance2 = vec2.distance(vec1);
        double distance3 = Vec2i.ZERO.distance(vec4);

        assertThat(distance1).isWithin(DOUBLE_FAULT_TOLERANCE).of(expectedDistance1);
        assertThat(distance2).isWithin(DOUBLE_FAULT_TOLERANCE).of(expectedDistance1);
        assertThat(distance3).isWithin(DOUBLE_FAULT_TOLERANCE).of(expectedDistance2);
    }

    @Test
    public void distance_returnsNaN_withMinMaxValues() {
        Vec2i vec1 = new Vec2i(Integer.MIN_VALUE, Integer.MIN_VALUE);
        Vec2i vec2 = new Vec2i(Integer.MAX_VALUE, Integer.MAX_VALUE);

        double distance1 = vec1.distance(vec2);
        double distance2 = Vec2i.ZERO.distance(vec1);

        assertThat(Double.isNaN(distance1)).isTrue();
        assertThat(Double.isNaN(distance2)).isTrue();
    }

    @Test(expected = NullPointerException.class)
    public void distance_null_throwsNPE() {
        new Vec2i(1, 2).distance(null);
    }

    @Test
    public void equals_behavesCorrectly() {
        Vec2i vec1 = new Vec2i(10, -5);
        Vec2i vec2 = new Vec2i(10, -5);
        Vec2i vec3 = new Vec2i(10, -4);

        assertThat(vec1).isEqualTo(vec1);
        assertThat(vec1).isEqualTo(vec2);
        assertThat(vec2).isEqualTo(vec1);

        assertThat(vec1).isNotEqualTo(vec3);
        assertThat(vec1).isNotEqualTo(null);
        assertThat(vec1).isNotEqualTo("(10, -5)");
    }


    @Test
    public void hashCode_isEqual_forEqualObjects() {
        Vec2i vec1 = new Vec2i(10, -5);
        Vec2i vec2 = new Vec2i(10, -5);

        int hashCode1 = vec1.hashCode();
        int hashCode2 = vec2.hashCode();

        assertThat(hashCode1).isEqualTo(hashCode2);
    }

    @Test
    public void hashCode_behavesCorrectly_inHashSets() {
        Vec2i vec1 = new Vec2i(10, -5);
        Vec2i vec2 = new Vec2i(10, -5);
        Vec2i vec3 = new Vec2i(10, -4);

        Set<Vec2i> set = new HashSet<>();
        set.add(vec1);

        assertThat(set).contains(vec2);
        assertThat(set).doesNotContain(vec3);
    }
}