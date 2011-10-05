/*
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA
 * 02111-1307, USA.
 *
 * http://www.gnu.org/copyleft/gpl.html
 */
package bone.server.server.utils;

import java.util.Random;

/**
 * <p>
 * 최저치 low와 최대치 high에 의해 둘러싸인, 수치의 범위를 지정하는 클래스.
 * </p>
 * <p>
 * <b>이 클래스는 동기화 되지 않는다.</b> 복수의 thread가 동시에 이 클래스의 인스턴스에 액세스 해,
 * 1개이상의 thread가 범위를 변경하는 경우, 외부적인 동기화가 필요하다.
 * </p>
 */
public class IntRange {
	private static final Random _rnd = new Random();
	private int _low;
	private int _high;

	public IntRange(int low, int high) {
		_low = low;
		_high = high;
	}

	public IntRange(IntRange range) {
		this(range._low, range._high);
	}

	/**
	 * 수치 i가, 범위내에 있을까를 돌려준다.
	 * 
	 * @param i
	 *            수치
	 * @return 범위내이면 true
	 */
	public boolean includes(int i) {
		return (_low <= i) && (i <= _high);
	}

	public static boolean includes(int i, int low, int high) {
		return (low <= i) && (i <= high);
	}

	/**
	 * 수치 i를, 이 범위내에 말다.
	 * 
	 * @param i
	 *            수치
	 * @return 말 수 있었던 값
	 */
	public int ensure(int i) {
		int r = i;
		r = (_low <= r) ? r : _low;
		r = (r <= _high) ? r : _high;
		return r;
	}

	public static int ensure(int n, int low, int high) {
		int r = n;
		r = (low <= r) ? r : low;
		r = (r <= high) ? r : high;
		return r;
	}

	/**
	 * 이 범위내로부터 랜덤인 값을 생성한다.
	 * 
	 * @return 범위내의 랜덤인 값
	 */
	public int randomValue() {
		return _rnd.nextInt(getWidth() + 1) + _low;
	}

	public int getLow() {
		return _low;
	}

	public int getHigh() {
		return _high;
	}

	public int getWidth() {
		return _high - _low;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof IntRange)) {
			return false;
		}
		IntRange range = (IntRange) obj;
		return (this._low == range._low) && (this._high == range._high);
	}

	@Override
	public String toString() {
		return "low=" + _low + ", high=" + _high;
	}
}
