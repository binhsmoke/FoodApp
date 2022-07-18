using System;
using System.Collections.Generic;

#nullable disable

namespace MasJoheun.Models
{
    public partial class Favourite
    {
        public string IsFavourited { get; set; }
        public int Id { get; set; }
        public string Phone { get; set; }

        public virtual Food IdNavigation { get; set; }
        public virtual Client PhoneNavigation { get; set; }
    }
}
