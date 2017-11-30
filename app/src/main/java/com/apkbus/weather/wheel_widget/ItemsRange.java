/*
 *  Android Wheel Control.
 *  https://code.google.com/p/android-wheel/
 *  
 *  Copyright 2011 Yuri Kanivets
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.apkbus.weather.wheel_widget;

/**
 * Range for visible items.
 */
class ItemsRange {
    // First item number
    private int first;

    // Items count
    private int count;

    /**
     * Default constructor. Creates an empty range
     */
    ItemsRange() {
        this(0, 0);
    }

    /**
     * @param first the number of first item
     * @param count the count of items
     */
    ItemsRange(int first, int count) {
        this.first = first;
        this.count = count;
    }//Constructor

    /**
     * @return the number of the first item
     */
    int getFirst() {
        return first;
    }//Gets number of  first item

    /**
     * @return the number of last item
     */
    int getLast() {
        return getFirst() + getCount() - 1;
    }//Gets number of last item

    /**
     * @return the count of items
     */
    int getCount() {
        return count;
    }//Get items count

    /**
     * @param index the item number
     * @return true if item is contained
     */
    boolean contains(int index) {
        return index >= getFirst() && index <= getLast();
    }//Tests whether item is contained by range
}