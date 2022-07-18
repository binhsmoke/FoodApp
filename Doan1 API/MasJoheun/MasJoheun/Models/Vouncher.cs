using System;
using System.Collections.Generic;

#nullable disable

namespace MasJoheun.Models
{
    public partial class Vouncher
    {
        public Vouncher()
        {
            Receipts = new HashSet<Receipt>();
        }

        public string Id { get; set; }
        public int Discount { get; set; }
        public string Description { get; set; }
        public int Condition { get; set; }
        public int Image { get; set; }

        public virtual ICollection<Receipt> Receipts { get; set; }
    }
}
