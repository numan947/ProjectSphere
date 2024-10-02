import {
  Card,
  CardBody,
  CardHeader,
  Checkbox,
  CheckboxGroup,
  Divider,
  Flex,
  Stack,
  Text,
} from "@chakra-ui/react";

interface FilterListProps {
  title: string;
  filters: string[];
  orientation?: "column" | "row";
  icon?: React.ReactNode;
}

const FilterList = ({ title, filters, orientation, icon }: FilterListProps) => {
  return (
    <>
      <Card padding={2} margin={3}>
        <CardHeader>
          <Flex justify="space-between">
            <Text fontWeight="bold" mb={2}>
              {title}
            </Text>
            {icon}
          </Flex>
          <Divider />
        </CardHeader>
        <CardBody>
          <CheckboxGroup colorScheme="green">
            <Stack spacing="auto" direction={orientation}>
              {filters.map((filter, index) => (
                <Checkbox key={index} value={filter} pl={2}>
                  {filter} -- {index}
                </Checkbox>
              ))}
            </Stack>
          </CheckboxGroup>
        </CardBody>
      </Card>
    </>
  );
};

export default FilterList;
