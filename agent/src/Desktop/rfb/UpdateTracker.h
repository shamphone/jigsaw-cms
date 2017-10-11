/* Copyright (C) 2002-2004 RealVNC Ltd.  All Rights Reserved.
 *    
 * This is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this software; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307,
 * USA.
 */

#ifndef __RFB_UPDATETRACKER_INCLUDED__
#define __RFB_UPDATETRACKER_INCLUDED__

#include "Rect.h"
#include "Region.h"
#include "PixelBuffer.h"

namespace rfb {

  class UpdateInfo {
  public:
    Region changed;
    Region copied;
    Point copy_delta;
    bool is_empty() const {
      return copied.is_empty() && changed.is_empty();
    }
    int numRects() const {
      return copied.numRects() + changed.numRects();
    }
  };

  class UpdateTracker {
  public:
    UpdateTracker() {};
    virtual ~UpdateTracker() {};

    virtual void add_changed(const Region &region) = 0;
    virtual void add_copied(const Region &dest, const Point &delta) = 0;
  };

  class ClippedUpdateTracker : public UpdateTracker {
  public:
    ClippedUpdateTracker(UpdateTracker &child_) : child(child_) {};
    ClippedUpdateTracker(UpdateTracker &child_,
      const Region &cliprgn_) : child(child_), cliprgn(cliprgn_) {};
    virtual ~ClippedUpdateTracker() {};

    virtual void set_clip_region(const Region cliprgn_) {cliprgn = cliprgn_;};

    virtual void add_changed(const Region &region);
    virtual void add_copied(const Region &dest, const Point &delta);
  protected:
    UpdateTracker &child;
    Region cliprgn;
  };

  class SimpleUpdateTracker : public UpdateTracker {
  public:
    SimpleUpdateTracker(bool use_copyrect=false);
    virtual ~SimpleUpdateTracker();

    virtual void enable_copyrect(bool enable);

    virtual void add_changed(const Region &region);
    virtual void add_copied(const Region &dest, const Point &delta);
    virtual void subtract(const Region& region);

    // Fill the supplied UpdateInfo structure with update information
    virtual void get_update(UpdateInfo* info, const Region& cliprgn);

    // Pass the current updates to the supplied tracker
    virtual void get_update(UpdateTracker &to) const;

    // Also removes the updates that are returned from this update tracker
    virtual void flush_update(UpdateTracker &to, const Region &cliprgn);


    // Get the changed/copied regions
    const Region& get_changed() const {return changed;}
    const Region& get_copied() const {return copied;}
    const Point& get_delta() const {return copy_delta;}

    // Move the entire update region by an offset
    void translate(const Point& p) {changed.translate(p); copied.translate(p);}

    virtual bool is_empty() const {return changed.is_empty() && copied.is_empty();}

    virtual void clear() {changed.clear(); copied.clear();};
  protected:
    Region changed;
    Region copied;
    Point copy_delta;
    bool copy_enabled;
  };

}

#endif // __RFB_UPDATETRACKER_INCLUDED__
