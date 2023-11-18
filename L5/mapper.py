import sys

for line in sys.stdin:
    fields = line.strip().split(",")

    if fields[0] == "Malaysia" and fields[2] == "Area Harvested":
        print(f"{fields[3]},{fields[5]}")
