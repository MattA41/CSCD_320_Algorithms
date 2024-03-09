'''
    Author: Dominic MacIsaac
    Feel free to add more tests to this file to ensure your code is working properly.
    To run
        - place this file in the same directory as the java file
        - In terminal, move to the directory containing the java file and this file
        - Run the command: python3 prog4_student_test.py
'''

import subprocess
import re

def write_array_to_file(array, filename):
    """
    Writes each item of an array to a new line in a file named filename.
    Inputs:
        array: List of items to write to the file.
        filename: Name of the file to write to.
    Outputs:
        None
    """
    with open(filename, 'w') as file:
        for item in array:
            file.write(str(item) + '\n')


def compile_java_code_indir(java_file_name):
    """Compiles the Java code and returns True if successful, False otherwise."""
    try:
        subprocess.check_output(["javac", java_file_name], stderr=subprocess.STDOUT)
        return True
    except subprocess.CalledProcessError:
        return False

def run_command(command_list):
    timeout = 10
    try:
        # Run the command and wait for it to complete
        output = subprocess.check_output(command_list, timeout=timeout, stderr=subprocess.STDOUT)
        return output.decode('utf-8').strip()
    except subprocess.TimeoutExpired:
        # Return a message if the process times out
        print("TIMEOUT")
        return f"Function timed out after {timeout} seconds"
    except subprocess.CalledProcessError as e:
        # Return the error output if the command fails
        return e.output.decode('utf-8').strip()
    except Exception as e:
        # General exception handling
        return str(e)

def read_numbers_from_file(file_name: str) ->[int]:
    """
    Read numbers from a file where each number is on a new line and return a list of numbers.
    
    Args:
    file_name (str): The name of the file to read from.
    
    Returns:
    List[int]: A list of numbers read from the file.
    """
    numbers = []
    with open(file_name, 'r') as file:
        for line in file:
            # Attempt to convert each line into an integer and append to the list
            try:
                number = int(line.strip())
                numbers.append(number)
            except ValueError:
                # Skip lines that cannot be converted to an integer
                continue
    
    return numbers

def write_to_file(r_string, file_name):
    """
    Write a raw string to a file.

    :param r_string: A raw string to be written to the file.
    :param file_name: The name of the file where the string will be written.
    """
    with open(file_name, 'w') as file:
        file.write(r_string)  


def extract_numbers(input_string):
    """
    Extract numbers from a string that are separated by commas, spaces, or both.

    :param input_string: A string containing numbers separated by commas and/or spaces.
    :return: A list of numbers extracted from the string.
    """
    # Using regular expression to find all numbers in the string
    try:
        numbers = re.findall(r'[\d.]+', input_string)
        
        # Converting each found string to an integer or a float
        return [int(num) if num.isdigit() else float(num) for num in numbers]
    except:
        return input_string
    
test_1_a = r'''0:1,2,3
1:3
2:
3:2'''

test_1_b = r'''1:3
0:1,2,3
2:
3:2'''

test_1_c = r'''1:3
0:2,1,3
3:2
2:'''
test_1_ans = [0,1,3,2]


if __name__ == '__main__':
    # Compile the java code
    print("Compile: ", str(compile_java_code_indir('TopoSort.java')))

    test_file_name = 'dag_test.txt'
    write_to_file(test_1_a, test_file_name)
    output = run_command(['java','TopoSort',test_file_name])
    print("Test 1a")
    print(f'Your output: {output}')
    output = extract_numbers(output)
    if test_1_ans == output:
        print("Passed!")
    else:
        print("failed")
    
    write_to_file(test_1_b, test_file_name)
    output = run_command(['java','TopoSort',test_file_name])
    print("Test 1b")
    print(f'Your output: {output}')
    output = extract_numbers(output)
    if test_1_ans == output:
        print("Passed!")
    else:
        print("failed")
    
    write_to_file(test_1_c, test_file_name)
    output = run_command(['java','TopoSort',test_file_name])
    print("Test 1c")
    print(f'Your output: {output}')
    output = extract_numbers(output)
    if test_1_ans == output:
        print("Passed!")
    else:
        print("failed")

