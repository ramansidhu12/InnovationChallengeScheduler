/*
 * Copyright 2020 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.acme.facilitylocation.domain;

import org.acme.facilitylocation.solver.FacilityLocationConstraintProvider;
import org.optaplanner.core.api.domain.entity.PlanningEntity;
import org.optaplanner.core.api.domain.variable.PlanningVariable;

/**
 * Consumer has a demand that can be satisfied by <em>any</em> {@link Facility} with a sufficient capacity.
 * <p/>
 * Closer facilities are preferred as the distance affects travel time, signal quality, etc.
 * This requirement is expressed by the
 * {@link FacilityLocationConstraintProvider#distanceFromFacility distance from facility} constraint.
 * <p/>
 * One of the FLP's goals is to minimize total set-up cost by selecting cheaper facilities. This requirement
 * is expressed by the {@link FacilityLocationConstraintProvider#setupCost setup cost} constraint.
 */
@PlanningEntity
public class Consumer {

    private long id;
    private Location location;
    private long demand;

    @PlanningVariable(valueRangeProviderRefs = "facilityRange")
    private Facility facility;

    public Consumer() {
    }

    public Consumer(long id, Location location, long demand) {
        this.id = id;
        this.location = location;
        this.demand = demand;
    }

    public boolean isAssigned() {
        return facility != null;
    }

    /**
     * Get distance from the facility.
     *
     * @return distance in meters
     */
    public long distanceFromFacility() {
        if (facility == null) {
            throw new IllegalStateException("No facility is assigned.");
        }
        return facility.getLocation().getDistanceTo(location);
    }

    public long getId() {
        return id;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public long getDemand() {
        return demand;
    }

    public void setDemand(long demand) {
        this.demand = demand;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    @Override
    public String toString() {
        return "Consumer " + id + " (" + demand + " dem)";
    }
}
